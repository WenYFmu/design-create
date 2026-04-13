---
name: "slidev_layouts"
description: "Layout definitions for default theme"
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
    
    <layout id="cover">
        <description>用于展示演示文稿封面页，可包含演示标题、背景信息等。</description>
    </layout>
    
    <layout id="default">
        <description>最基础的布局，可展示任意类型内容。</description>
    </layout>
    
    <layout id="end">
        <description>演示文稿的结束页面。</description>
    </layout>
    
    <layout id="fact">
        <description>以突出方式展示事实或数据。</description>
    </layout>
    
    <layout id="full">
        <description>使用全屏幕空间展示内容。</description>
    </layout>
    
    <layout id="image-left">
        <description>在屏幕左侧显示图像，内容将置于右侧。</description>
        <usage>
            <code>
---
layout: image-left

# 图像源
image: /path/to/the/image

# 内容区域的自定义类名
class: my-cool-content-on-the-right
---
            </code>
        </usage>
    </layout>
    
    <layout id="image-right">
        <description>在屏幕右侧显示图像，内容将置于左侧。</description>
        <usage>
            <code>
---
layout: image-right

# 图像源
image: /path/to/the/image

# 内容区域的自定义类名
class: my-cool-content-on-the-left
---
            </code>
        </usage>
    </layout>
    
    <layout id="image">
        <description>将图像作为页面主内容展示。</description>
        <usage>
            <code>
---
layout: image

# 图像源
image: /path/to/the/image
---
可通过 backgroundSize 属性修改默认背景尺寸（cover）：

---
layout: image
image: /path/to/the/image
backgroundSize: contain
---

---
layout: image-left
image: /path/to/the/image
backgroundSize: 20em 70%
---
            </code>
        </usage>
    </layout>
    
    <layout id="iframe-left">
        <description>在屏幕左侧显示网页，内容将置于右侧。</description>
        <usage>
            <code>
---
layout: iframe-left

# 网页源地址
url: https://github.com/slidevjs/slidev

# 内容区域的自定义类名
class: my-cool-content-on-the-right
---
            </code>
        </usage>
    </layout>
    
    <layout id="iframe-right">
        <description>在屏幕右侧显示网页，内容将置于左侧。</description>
        <usage>
            <code>
---
layout: iframe-right

# 网页源地址
url: https://github.com/slidevjs/slidev

# 内容区域的自定义类名
class: my-cool-content-on-the-left
---
            </code>
        </usage>
    </layout>
    
    <layout id="iframe">
        <description>将网页作为页面主内容展示。</description>
        <usage>
            <code>
---
layout: iframe

# 网页源地址
url: https://github.com/slidevjs/slidev
---
            </code>
        </usage>
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
``