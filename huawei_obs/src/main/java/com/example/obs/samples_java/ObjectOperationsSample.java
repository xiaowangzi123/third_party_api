package com.example.obs.samples_java;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.exception.ObsException;
import com.obs.services.internal.utils.ServiceUtils;
import com.obs.services.model.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * This sample demonstrates how to do object-related operations
 * (such as create/delete/get/copy object, do object ACL/OPTIONS)
 * on OBS using the OBS SDK for Java.
 */
public class ObjectOperationsSample {
    private static final String endPoint = "https://obs.cn-south-1.myhuaweicloud.com";

    private static final String ak = "Z9WKE1NSR5IHFCWZRYCB";

    private static final String sk = "nkw4R0qIthHd4CsDZccqAwN9elpCYFHvYsqhmSxw";

    private static ObsClient obsClient;

    private static final String bucketName = "picture-702d";

    private static String objectKey = "my-obs-object-key-demo";

    public static void main(String[] args)
            throws IOException {
        ObsConfiguration config = new ObsConfiguration();
        config.setSocketTimeout(30000);
        config.setConnectionTimeout(10000);
        config.setEndPoint(endPoint);
        try {
            /*
             * Constructs a obs client instance with your account for accessing OBS
             */
            obsClient = new ObsClient(ak, sk, config);

            /*
             * Create bucket
             */
            obsClient.createBucket(bucketName);

            /*
             * Create object
             */
            String content = "Hello OBS";
            obsClient.putObject(bucketName, objectKey, new ByteArrayInputStream(content.getBytes("UTF-8")), null);
            System.out.println("Create object:" + objectKey + " successfully!\n");

            /*
             * Get object metadata
             */
            System.out.println("Getting object metadata");
            ObjectMetadata metadata = obsClient.getObjectMetadata(bucketName, objectKey, null);
            System.out.println("\t" + metadata);

            /*
             * Get object
             */
            System.out.println("Getting object content");
            ObsObject obsObject = obsClient.getObject(bucketName, objectKey, null);
            System.out.println("\tobject content:" + ServiceUtils.toString(obsObject.getObjectContent()));

            /*
             * Copy object
             */
            String sourceBucketName = bucketName;
            String destBucketName = bucketName;
            String sourceObjectKey = objectKey;
            String destObjectKey = objectKey + "-back";
            System.out.println("Copying object\n");
            obsClient.copyObject(sourceBucketName, sourceObjectKey, destBucketName, destObjectKey);

            /*
             * Options object
             */
            doObjectOptions();

            /*
             * Put/Get object acl operations
             */
            doObjectAclOperations();

            /*
             * Delete object
             */
            System.out.println("Deleting objects\n");
            obsClient.deleteObject(bucketName, objectKey, null);
            obsClient.deleteObject(bucketName, destObjectKey, null);
        } catch (ObsException e) {
            System.out.println("Response Code: " + e.getResponseCode());
            System.out.println("Error Message: " + e.getErrorMessage());
            System.out.println("Error Code:       " + e.getErrorCode());
            System.out.println("Request ID:      " + e.getErrorRequestId());
            System.out.println("Host ID:           " + e.getErrorHostId());
        } finally {
            if (obsClient != null) {
                try {
                    obsClient.close();
                } catch (IOException e) {
                }
            }
        }

    }

    private static void doObjectOptions()
            throws ObsException {

        BucketCors bucketCors = new BucketCors();
        BucketCorsRule rule = new BucketCorsRule();
        rule.getAllowedHeader().add("Authorization");
        rule.getAllowedOrigin().add("http://www.a.com");
        rule.getAllowedOrigin().add("http://www.b.com");
        rule.getExposeHeader().add("x-obs-test1");
        rule.getExposeHeader().add("x-obs-test2");
        rule.setMaxAgeSecond(100);
        rule.getAllowedMethod().add("HEAD");
        rule.getAllowedMethod().add("GET");
        rule.getAllowedMethod().add("PUT");
        bucketCors.getRules().add(rule);
        obsClient.setBucketCors(bucketName, bucketCors);

        System.out.println("Options object\n");
        OptionsInfoRequest optionInfo = new OptionsInfoRequest();
        optionInfo.setOrigin("http://www.a.com");
        optionInfo.getRequestHeaders().add("Authorization");
        optionInfo.getRequestMethod().add("PUT");
        System.out.println(obsClient.optionsObject(bucketName, objectKey, optionInfo));
    }

    private static void doObjectAclOperations()
            throws ObsException {
        System.out.println("Setting object ACL to public-read \n");

        obsClient.setObjectAcl(bucketName, objectKey, AccessControlList.REST_CANNED_PUBLIC_READ);

        System.out.println("Getting object ACL " + obsClient.getObjectAcl(bucketName, objectKey) + "\n");

        System.out.println("Setting object ACL to private \n");

        obsClient.setObjectAcl(bucketName, objectKey, AccessControlList.REST_CANNED_PRIVATE);

        System.out.println("Getting object ACL " + obsClient.getObjectAcl(bucketName, objectKey) + "\n");
    }
}
