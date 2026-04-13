---
name: "slidev-theme-penguin"
description: "Generate Slidev presentations using the Penguin theme. Invoke when user wants to create developer-friendly presentations with intro, presenter, text-image, and text-window layouts."
---

# Slidev Penguin Theme

Generate developer-friendly presentations with Slidev using the Penguin theme.

## Installation

```bash
npm install slidev-theme-penguin
```

## Available Layouts

| Layout | Description | Parameters |
|--------|-------------|------------|
| `intro` | Introduction page with logos | themeConfig |
| `presenter` | Speaker info with image | - |
| `new-section` | Section divider | - |
| `text-image` | Text with image | image |
| `text-window` | Text with code/demo window | - |
| `two-cols` | Two columns | - |
| `two-cols-header` | Header + two columns | - |

## Intro Layout

```yaml
---
layout: intro
---

# Title

Content with theme logos
```

## Presenter Layout

```yaml
---
layout: presenter
---

# Speaker Name

Speaker bio and information
```

## Text-Image Layout

```yaml
---
layout: text-image
image: ./path/to/image.png
---

# Content

Text appears alongside the image
```

## Text-Window Layout

```yaml
---
layout: text-window
---

# Demo

Content with a code/demo window
```

## New Section Layout

```yaml
---
layout: new-section
---

# Chapter 1

Section divider page
```

## Reference Files

- `outline_generate.md` - Generate presentation outline
- `slidev_generate.md` - Base slide generation
- `slidev_generate_with_specific_outlines.md` - Generate with specific outlines
- `user_info.md` - User information template
- `extension.md` - Extension commands
- `slidev_animation.md` - Animation syntax
- `slidev_layouts.md` - Layout definitions
- `example.md` - Example presentation
