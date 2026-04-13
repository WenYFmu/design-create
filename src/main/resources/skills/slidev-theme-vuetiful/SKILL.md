---
name: "slidev-theme-vuetiful"
description: "Generate Slidev presentations using the Vuetiful theme. Invoke when user wants to create Vue-focused presentations with SFC playground, big-points, quote, and video layouts."
---

# Slidev Vuetiful Theme

Generate Vue-focused presentations with Slidev using the Vuetiful theme.

## Installation

```bash
npm install slidev-theme-vuetiful
```

## Available Layouts

| Layout | Description | Parameters |
|--------|-------------|------------|
| `default` | Improved grid-based layout | cols |
| `cover` | Animated cover page | - |
| `big-points` | Emphasized bullet points | - |
| `section` | Section divider | - |
| `quote` | Quote display | - |
| `sfc` | Vue SFC playground | - |
| `video` | Video embedding | video, autoplay |
| `full-image` | Full screen image | image |
| `outro` | Thank you page | - |

## Default Layout with Columns

```yaml
---
layout: default
cols: 2-1  # 2/3 left, 1/3 right
---

# Left Content (wider)

::right::

# Right Content (narrower)
```

## Cover Layout

```yaml
---
layout: cover
---

# Presentation Title

Animated cover with background effects
```

## Big Points Layout

```yaml
---
layout: big-points
---

# Key Takeaways

- Point 1
- Point 2
- Point 3
```

## Quote Layout

```yaml
---
layout: quote
---

"The best code is no code at all."

- Anonymous
```

## SFC Playground Layout

```yaml
---
layout: sfc
---

# Vue SFC Demo

Interactive Vue Single File Component playground
```

## Video Layout

```yaml
---
layout: video
video: ./video.mp4
autoplay: true
---
```

## Full Image Layout

```yaml
---
layout: full-image
image: ./background.jpg
---

# Content over full image
```

## Outro Layout

```yaml
---
layout: outro
---

# Thank You!

Questions?
```

## Reference Files

- `outline_generate.md` - Generate presentation outline
- `slidev_generate.md` - Base slide generation
- `slidev_generate_with_specific_outlines.md` - Generate with specific outlines
- `user_info.md` - User information template
- `extension.md` - Extension commands
- `slidev_animation.md` - Animation syntax
- `slidev_layouts.md` - Layout definitions
