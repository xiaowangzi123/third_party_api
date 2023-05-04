package com.onem.service;

import com.alibaba.fastjson.JSONObject;
import com.cloudtranslation.dat.constants.DownConstants;
import com.cloudtranslation.dat.enums.EntityEnum;
import com.cloudtranslation.dat.util.datahandle.DataHandleUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.trees.international.pennchinese.ChineseGrammaticalStructure;
import edu.stanford.nlp.util.CoreMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;



@Slf4j
@Service
public class ToolsServiceImpl implements ToolsService {
    @Resource
    private StanfordCoreNLP stanfordCoreNLP;
    @Resource
    private LexicalizedParser lexicalizedParser;

    private static final String TAIWAN_STR = "Taiwan";

    @Resource
    private RestTemplate restTemplate;

    @Value("${seg.split.url}")
    private String contentSplitUrl;

    private static final String SUCCESS_CODE = "20000";

    @Override
    public String entityTag(String content) {
        long t1 = System.currentTimeMillis();
        Annotation document = new Annotation(content);
        stanfordCoreNLP.annotate(document);
        //处理之后的结果
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        StringBuilder tagContent = new StringBuilder();
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);            // 获取分词
                String type = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);    // 获取命名实体识别结果

            }
            tagContent.append("\n\r");
        }
        log.info("实体标注耗时:{}", System.currentTimeMillis() - t1);
        return tagContent.toString();
    }

    @Override
    public String syntaxTag(String content) {
        //去掉p标签
        long t1 = System.currentTimeMillis();
        Annotation document = new Annotation(content);
        stanfordCoreNLP.annotate(document);
        //处理之后的结果
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        StringBuilder tagContent = new StringBuilder();
        for (CoreMap sentence : sentences) {
            Tree tree = lexicalizedParser.parse(sentence.toString());
            ChineseGrammaticalStructure gs = new ChineseGrammaticalStructure(tree);
            Collection<TypedDependency> tdl = gs.typedDependenciesCollapsed();
            //获取标注后词语
            for (TypedDependency typedDependency : tdl) {
                //句法分析后的词语
                String participleWord = typedDependency.dep().toString();
                String type = typedDependency.reln().toString();
                //单词
                String word = participleWord.split("/")[0];
//                        String type = participleWord.split("/")[1];
                tagContent.append(word + "_" + type + " ");
            }
            tagContent.append("\n\r");
        }
        log.info("句法标注耗时:{}", System.currentTimeMillis() - t1);
        return tagContent.toString();
    }

    public BFNode convertToBFSTree(Tree coreNLPTree) {
        String label = coreNLPTree.label().toString();
        BFRule rule = BFRule.fromLabel(label);
        BFNode node = new BFNode(rule);

        List<Tree> children = coreNLPTree.childrenAsList();
        if (!children.isEmpty()) {
            List<BFNode> childNodes = new ArrayList<>();
            for (Tree child : children) {
                BFNode childNode = convertToBFSTree(child);
                childNodes.add(childNode);
            }
            node.setChildren(childNodes);
        }

        return node;
    }

    public String syntaxTag2(String content) {
        //去掉p标签
        long t1 = System.currentTimeMillis();
        Annotation document = new Annotation(content);
        stanfordCoreNLP.annotate(document);
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        StringBuilder tagContent = new StringBuilder();
        for (CoreMap sentence : sentences) {
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            tagContent.append(tree).append("\n");
        }
        log.info("句法标注耗时:{}", System.currentTimeMillis() - t1);
        return tagContent.toString();
    }

    @Override
    public String posTagging(String content) {
        long t1 = System.currentTimeMillis();
//        //去掉p标签
        Annotation document = new Annotation(content);
        stanfordCoreNLP.annotate(document);
        //处理之后的结果
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        StringBuilder tagContent = new StringBuilder();
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // 获取分词
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // 获取词性标注
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

            }
            tagContent.append("\n\r");
        }
        log.info("词性标注耗时:{}", System.currentTimeMillis() - t1);
        return tagContent.toString();
    }

    @Override
    public List<String> contentSegSplit(String content) {
        if (StringUtils.isEmpty(content)) {
            return Collections.emptyList();
        }
        try {

        } catch (Exception e) {
            e.printStackTrace();
            log.info("句子切分出现错误");
        }
        return Collections.emptyList();
    }


}
