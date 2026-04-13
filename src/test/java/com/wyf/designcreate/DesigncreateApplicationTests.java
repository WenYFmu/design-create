package com.wyf.designcreate;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Assert;
import com.wyf.designcreate.ai.aiserver.codegen.AiCodeGeneratorService;
import dev.langchain4j.community.store.memory.chat.redis.RedisChatMemoryStore;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.skills.*;
import jakarta.annotation.Resource;
import org.aspectj.apache.bcel.util.ClassPath;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

@SpringBootTest
class DesigncreateApplicationTests {

    @Resource
    private ChatModel openAiChatModel;

    @Resource
    private RedisChatMemoryStore redisChatMemoryStore;

    @Test
    void skillLoads() {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:skills");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Path path = file.toPath();
        List<FileSystemSkill> skillsFile = FileSystemSkillLoader.loadSkills(path);
        Assert.notEmpty(skillsFile);
        Skills skills = Skills.from(skillsFile);
        AiCodeGeneratorService service = AiServices.builder(AiCodeGeneratorService.class)
                .chatModel(openAiChatModel)
                .toolProviders(skills.toolProvider()) // or .toolProviders(myToolProvider, skills.toolProvider()) if you already have a tool provider configured
                .systemMessage("You have access to the following skills:\n" + skills.formatAvailableSkills()
                        + "\nWhen the user's request relates to one of these skills, activate it first using the `activate_skill` tool before proceeding." +
                        "你是SlidevPPT制作专家，你需要根据提供的PPT大纲制作Slidev的PPT" +
                        "要求：精致、配图、适当动效、布局合理不要超出页面，给出md源码" +
                        "语法：严格遵守slidev语法，可以参考skills")
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                .build();
        String s = " 第一部分：PPT大纲结构 (共10页)\n" +
                "第1页：封面\n" +
                "\n" +
                "标题：速度与激情——公路车入门与进阶指南\n" +
                "\n" +
                "副标题：从认识构造到纵享骑行\n" +
                "\n" +
                "制作人/日期\n" +
                "\n" +
                "第2页：什么是公路车？\n" +
                "\n" +
                "公路车的定义与核心特征（弯把、细胎、轻量化）\n" +
                "\n" +
                "公路车 vs 山地车：设计哲学与用途差异\n" +
                "\n" +
                "第3页：公路车的四大主流类型\n" +
                "\n" +
                "气动（破风）车、爬坡（轻量）车、耐力（长途）车、综合车\n" +
                "\n" +
                "分类图解与适用人群匹配\n" +
                "\n" +
                "第4页：车架材质全解析\n" +
                "\n" +
                "铝合金、碳纤维、钛合金、钢架的优缺点与选购建议\n" +
                "\n" +
                "第5页：公路车的“心脏”与“腿脚”\n" +
                "\n" +
                "变速套件等级（Shimano / SRAM 阶梯介绍）\n" +
                "\n" +
                "轮组与外胎（铝轮 vs 碳刀，框高选择，真空胎优势）\n" +
                "\n" +
                "第6页：新手购车选购指南\n" +
                "\n" +
                "预算与配置的匹配逻辑\n" +
                "\n" +
                "各价位段代表车型推荐（2000元-10000元）\n" +
                "\n" +
                "避坑指南（关于一体把、全内走线、圈刹碟刹选择）\n" +
                "\n" +
                "第7页：骑行安全与基础技巧\n" +
                "\n" +
                "15条新手保命口诀\n" +
                "\n" +
                "正确的骑行姿势与刹车技巧\n" +
                "\n" +
                "第8页：骑行装备清单\n" +
                "\n" +
                "绝对必备（头盔、骑行裤、车灯、打气筒）\n" +
                "\n" +
                "进阶推荐（锁踏锁鞋、码表、骑行服）\n" +
                "\n" +
                "第9页：品牌巡礼与进阶改装思路\n" +
                "\n" +
                "主流品牌盘点（捷安特、美利达、闪电、崔克、喜德盛等）\n" +
                "\n" +
                "万元内入门/进阶代表车型推荐\n" +
                "\n" +
                "最值得的升级顺序（轮胎 > 轮组 > 把组 > 套件）\n" +
                "\n" +
                "第10页：保养知识与结语\n" +
                "\n" +
                "日常检查项目与简单保养周期\n" +
                "\n" +
                "结语：享受骑行，安全第一\n" +
                "\n" +
                "\uD83D\uDCDD 第二部分：配套演讲文案\n" +
                "建议在PPT备注中填入以下要点，演示时进行脱稿演讲。\n" +
                "\n" +
                "第1页：封面\n" +
                "大家好，今天我要分享的主题是《公路车入门与进阶指南》。无论你是准备入手第一辆车的纯新手，还是已经骑行一段时间的爱好者，希望这份指南都能帮你少走弯路，真正享受公路车带来的速度与自由。\n" +
                "\n" +
                "第2页：什么是公路车？\n" +
                "公路车，顾名思义是为铺装公路追求速度而设计的自行车。它的标志性特征很明显：下弯的弯把是为了让骑手获得低风阻的破风姿势；极细且光滑的轮胎则是为了最大限度地降低滚动阻力；整车设计一切围绕轻量化和高效率展开。相比坐姿直立、拥有避震前叉的山地车，公路车在铺装路面上骑行时平均速度能高出5-10km/h，那种贴地飞行的快感是其他车型无法比拟的。\n" +
                "\n" +
                "第3页：公路车的四大主流类型\n" +
                "市面上的公路车主要分为四大类：气动车追求极致空气动力学，拥有扁平粗壮的管型和激进低趴的骑行姿势，适合平路高速巡航和比赛冲刺-1。爬坡车追求轻量化，纤细圆润的管型配上更舒适的几何，是攻克高山陡坡的利器，也非常适合新手入门。耐力车头管更高，几何直立，是长途骑行最舒适的选择。而综合车则介于爬坡与破风之间，兼顾了轻量、气动与舒适，是大多数普通爱好者的明智之选-5。如果你不确定怎么选，综合车架通常是最稳妥的起点。\n" +
                "\n" +
                "第4页：车架材质全解析\n" +
                "车架是公路车的灵魂，不同材质决定了不同的骑行体验。铝合金是性价比之王，刚性强、动力传递直接，但路感偏颠簸，是预算有限新手的入门首选-18。碳纤维重量极轻且能有效吸收路面震动，造型自由度极高，是目前高性能公路车的主流选择，但怕磕碰，价格也更高-18。钛合金强度高、寿命长，路感极为平顺，但价格昂贵，是小众高端玩家的挚爱。钢架吸震性好、极为耐用，但偏重，更多用于旅行车或复古情怀玩家-5。\n" +
                "\n" +
                "第5页：公路车的“心脏”与“腿脚”\n" +
                "公路车的核心性能很大程度取决于两大部件——变速套件与轮组。套件方面，禧玛诺（Shimano）是市场主流，入门级Claris、Sora到专业级的105、Ultegra、Dura-Ace，等级越高换挡越顺畅、重量越轻-24。预算有限的话，禧玛诺105级别是性价比最高的专业入门选择-。轮组则是影响速度最直观的“三大件”之一，框高是关键参数：38-50mm是兼顾平路气动和爬坡灵活的全能选择；50mm以上是为高速巡航而生的气动轮组，但受侧风影响较大-29。从铝轮升级到碳纤维轮组，是整车性能提升最立竿见影的改装。\n" +
                "\n" +
                "第6页：新手购车选购指南\n" +
                "买车前先问自己两个问题：预算多少？主要骑什么路线？入门核心预算区间在2000-3000元，这个价位的车型在基础性能、性价比和售后服务之间取得了较好的平衡-40。预算2000-3000元，优先考虑喜德盛AD300、捷安特SCR、美利达Scultura 93等成熟车型；预算3000-5000元，可以买到铝合金车架搭配更好套件和油压碟刹的进阶车型；预算8000-10000元，则能触及入门级碳纤维车架和专业级套件-39。避坑方面：在没有足够骑行经验前，不建议盲目更换碳纤维一体把；预算有限时圈刹完全够用，不必强求全内走线车型-31。\n" +
                "\n" +
                "第7页：骑行安全与基础技巧\n" +
                "公路车速度快，安全是第一位。刹车原则：前刹制动力占70%，后刹占30%，高速急刹时臀部向后移防止前翻-56。过弯技巧：进弯前提前减速，弯中尽量不刹车，白线、井盖千万别压，雨天极易打滑-59。跟车距离：跟车时保持前车后轮与你前轮至少1-2米的距离，前车急刹才反应得过来-59。骑行时两边刹把各放一根手指，随时准备调整速度-。第一次上路，建议选择15公里左右、平坦少车的路线，先适应公路车的操控感受-62。\n" +
                "\n" +
                "第8页：骑行装备清单\n" +
                "装备可以分为“必须买”和“可以缓”两类。绝对必备：一顶通过安全认证的头盔，这是保护生命的最后防线-69；一条带坐垫的骑行裤，能极大提升长途舒适度；前后车灯，即使白天也建议开启闪烁模式提升可见度；一个带压力表的落地打气筒，公路车胎压需精确到80-130psi-69。进阶推荐：锁踏+锁鞋能显著提高踩踏效率，但建议先用平踏适应操控后再尝试-69；码表可以记录骑行数据，让训练更有方向。\n" +
                "\n" +
                "第9页：品牌巡礼与进阶改装思路\n" +
                "市场主流品牌各有所长：捷安特、美利达作为台湾双雄，门店覆盖广，售后最有保障；闪电、崔克是国际高端代表，技术实力顶尖；喜德盛、瑞豹等国产品牌性价比极高-40。如果你已经拥有公路车并想要升级，最值得的改装顺序是：外胎 > 轮组 > 把组 > 套件。顶级真空胎能以相对较小的花费带来滚动阻力、抓地力和舒适性的全面跃升，是性价比最高的升级-31；升级碳纤维轮组则是感受最直观的性能飞跃，踩踏响应速度和巡航能力都会有质的提升-31。套件升级到105级别以上后，边际收益递减，不必盲目追高-31。\n" +
                "\n" +
                "第10页：保养知识与结语\n" +
                "公路车需要定期保养才能保持最佳状态。每次骑行前：检查胎压是否在推荐范围内、刹车是否灵敏、链条是否干净。每周：清洁链条并上油，检查刹车片磨损情况。每月：检查各螺丝是否松动，辐条张力是否均匀。骑行不只是运动，更是一种生活方式。无论你是为了健身、通勤还是享受速度，安全永远是第一课。愿每一位骑友都能平安出发，尽兴而归！";
        String generated = service.generateSlidevCode("列出你看到的所有skills");
        System.out.println(generated);
//        String generated1 = service.generateSlidevCode("你会使用Academic主题吗？");
//        System.out.println(generated1);
        String generated2 = service.generateSlidevCode("请生成一个关于“如何使用Slidev”的PPT，使用vuetiful主题");
        System.out.println(generated2);
    }

}
