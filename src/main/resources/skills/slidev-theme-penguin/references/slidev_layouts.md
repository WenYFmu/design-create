---
name: "slidev_layouts"
description: "Layout definitions for penguin theme"
---

# slidev_layouts

## Content

``xml
<BUILD-IN-LAYOUTS>
本页面列出了 Slidev 提供的所有内置布局。这些布局可通过幻灯片的 frontmatter 的 layout 这个 key 来设置，比如：

```md
---
layout: cover
---
```

请注意 📖 主题与插件 📖 主题与插件 可能会提供额外布局或覆盖现有布局。如需添加自定义布局，请参阅 📖 编写布局 📖 编写布局。

<layout id="center"> ... </layout> 代表 center 这个布局的介绍和用法。

<layouts>
    <layout id="center">
        <description>将内容显示在屏幕中央。</description>
    </layout>
        
    <layout id="default">
        <description>最基础的布局，可展示任意类型内容。</description>
    </layout>
    
    <layout id="end">
        <description>演示文稿的结束页面。</description>
    </layout>
    
    <layout id="two-cols">
        <description>将页面内容分为两栏。</description>
        <usage>
            <code>
---
layout: two-cols
---

# 左侧

此处内容显示在左侧

::right::

# 右侧

此处内容显示在右侧
            </code>
        </usage>
    </layout>
    
    <layout id="two-cols-header">
        <description>将页面内容分为上下两行，第二行再分割为左右两栏。</description>
        <usage>
            <code>
---
layout: two-cols-header
---

跨栏内容

::left::

# 左侧

此处内容显示在左侧

::right::

# 右侧

此处内容显示在右侧
            </code>
        </usage>
    </layout>
</layouts>

</BUILD-IN-LAYOUTS>

<PENGUIN-LAYOUTS>
<documentation>
    <section name="布局 (Layouts)">
        <layout id="intro">
            <description>介绍页布局，适用于放在第一页</description>
            <usage>
                <code>
---
theme: penguin
layout: intro
themeConfig:
  logoHeader: '/avatar.png'
  eventLogo: 'https://img2.storyblok.com/352x0/f/84560/2388x414/23d8eb4b8d/vue-amsterdam-with-name.png'
  eventUrl: 'https://vuejs.amsterdam/'
  twitter: '@alvarosabu'
  twitterUrl: 'https://twitter.com/alvarosabu'
---

# A penguin Slidev Theme

🐧 slides for developers

<div class="pt-12">
  <span @click="next" class="px-2 p-1 rounded cursor-pointer hover:bg-white hover:bg-opacity-10">
    Press Space for next page <carbon:arrow-right class="inline"/>
  </span>
</div>
                </code>
            </usage>
        </layout>

        <layout id="presenter">
            <description>演讲者布局</description>
            <parameters>
                <parameter name="presenterImage" type="String" description="演讲者图片URL"/>
            </parameters>
            <usage>
                <code>
---
layout: presenter
presenterImage: 'https://pic1.zhimg.com/80/v2-9a0b6e0ee617ae4e12ef22c628ff8451_1440w.png'
---
# 锦恢（黄哲龙）

- 我是 [**OpenMCP**](https://github.com/LSTM-Kirigaya/openmcp-client) 和 [**SlidevAI**](https://github.com/LSTM-Kirigaya/slidev-ai) 等的作者。
- 知乎科技领域的知势榜博主，发表技术博客累计 300 多篇。多篇博客被大学讲义和自媒体引用。
- 深度参与国内第一个大模型训练框架 **ColossalAI** 的开发。
- 今年秋招以硕士身份拿下国内某大厂人才计划。
- 欢迎在 [**知乎**](https://www.zhihu.com/people/can-meng-zhong-de-che-xian), [**B站**](https://space.bilibili.com/434469188?spm_id_from=333.1007.0.0) 和 [**我的个人网站**](https://kirigaya.cn) 关注我。
                </code>
            </usage>
        </layout>

        <layout id="new-section">
            <description>新章节布局，适用于新的一章开头前加入</description>
            <usage>
                <code>
---
layout: new-section
---
# Sec.1 从大模型到 AI Agent

![penguin-work](https://picx.zhimg.com/80/v2-9721a7259f0cb53af341b12850d3bfb4_1440w.png)
                </code>
            </usage>
        </layout>

        <layout id="text-image">
            <description>文本图像布局，适用于展示单张图片+文本描述的布局</description>
            <parameters>
                <parameter name="media" type="String" description="媒体资源URL"/>
                <parameter name="reverse" type="Boolean" default="false" description="是否反转布局顺序"/>
            </parameters>
            <usage>
                <code>
---
layout: text-image
media: https://picx.zhimg.com/100/v2-d726780b6b324174e2ea4265a456f7df_r.jpg
caption: 'I am a penguin'
---

# This is a peguin 🐧

Arepa ipsum dolor amet jalabola! aenean sit tequeños se prendio esta chamito;? Nisl nojoda eu amet in? Nisl cuál es la guachafita ni lava ni presta la batea háblame cloro gravida sifrino macundal panita; Sed háblame cloro nunc empanada ac coroto Na webona vladimil parchita?

- Cacique panita sit Se prendio la labia gravida Praesent tequeño.
- Qué paso mi pana?! elit parchita molleja aguacate vergación, háblame mollejúo chamito est burda mauris morbi;
                </code>
            </usage>
        </layout>

        <layout id="text-window">
            <description>文本窗口布局，适用于展示代码和流程图</description>
            <parameters>
                <parameter name="reverse" type="Boolean" default="false" description="是否反转布局顺序"/>
            </parameters>
            <usage>
                <code>
---
layout: text-window
reverse: true
---

# Consoles

Use code snippets and get the highlighting directly into a nice looking window!

::window::

```py
from openai import OpenAI
client = OpenAI(api_key="your_api_key")

# 定义函数
def get_weather(city: str):
    return {"北京": "28°C", "上海": "30°C"}.get(city, "未知")

# 函数 schema
functions = [{
    "name": "get_weather",
    "parameters": {
        "type": "object",
        "properties": {"city": {"type": "string"}},
        "required": ["city"]
    }
}]

# 用户提问
resp = client.chat.completions.create(
    model="gpt-4o-mini",
    messages=[{"role": "user", "content": "北京天气怎么样？"}],
    functions=functions,
    function_call="auto"
)

# 执行并返回结果
args = eval(resp.choices[0].message.function_call.arguments)
result = get_weather(**args)
print("最终回答:", result)
```
                </code>
            </usage>
        </layout>
    </section>

    <section name="组件 (Components)">
        <component name="fancy-link">
            <description>自动添加favicon的链接组件</description>
            <usage>
                <code>
Say hi at &lt;fancy-link href="https://twitter.com/alvarosabu"&gt;@alvarosabu&lt;/fancy-link&gt;
                </code>
            </usage>
        </component>
    </section>
</documentation>
</PENGUIN-LAYOUTS>
``