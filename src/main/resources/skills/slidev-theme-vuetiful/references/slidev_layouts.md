---
name: "slidev_layouts"
description: "Layout definitions for vuetiful theme"
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

        <layouts>
            <layout id="default">
                <description>改进版的默认布局，基于 grid，更加灵活。</description>
                <usage>
                    <code>
---
layout: default

# 标题
正文或列表内容
---

# 使用单独的标题行
---
title: This will be the heading
titleRow: true
---

内容会和标题分开渲染，便于布局

# 使用列布局
---
cols: 2-1
---

左侧内容

:::right:::

右侧内容
---
            </code>
                </usage>
            </layout>

            <layout id="cover">
                <description>演讲封面页，支持多种样式和动画。</description>
                <usage>
                    <code>
---
layout: cover
cover: alt   # 可选：启用不同风格的封面
clicks: 1    # 保证入场动画正常
---

# 演讲标题
副标题或作者
---
            </code>
                </usage>
            </layout>

            <layout id="big-points">
                <description>放大字号并居中展示的要点页，适合突出少量重点。</description>
                <usage>
                    <code>
---
layout: big-points
title: Need to make a few big points?
titleRow: true
---

- Increased font size
- Centered content
- Helps stressing a few points
---
            </code>
                </usage>
            </layout>

            <layout id="section">
                <description>用于分割演讲章节的幻灯片，通常只包含一个标题。</description>
                <usage>
                    <code>
---
layout: section
---

# Part I - Introduction
            </code>
                </usage>
            </layout>

            <layout id="quote">
                <description>突出展示引用或名言，适合强调观点。</description>
                <usage>
                    <code>
---
layout: quote
author: Linus Borg (2021)
---

# "Big quotes make your talk look fancy"
---
            </code>
                </usage>
            </layout>

            <layout id="sfc">
                <description>集成 Vue SFC Playground，可展示单文件组件或多文件示例。</description>
                <usage>
                    <code>
---
layout: sfc
example: Test
---

# 示例标题
---
            </code>
                </usage>
            </layout>

            <layout id="video">
                <description>展示视频内容的页面。</description>
                <usage>
                    <code>
---
layout: video
src: /path/to/video.mp4
---
            </code>
                </usage>
            </layout>

            <layout id="full-image">
                <description>全屏展示一张图片。</description>
                <usage>
                    <code>
---
layout: full-image
image: coverImage.png   # 必须放在 /public 下
---
            </code>
                </usage>
            </layout>

            <layout id="outro">
                <description>演讲结束页。</description>
                <usage>
                    <code>
---
layout: outro
---

# Thank You!
---
            </code>
                </usage>
            </layout>
        </layouts>


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