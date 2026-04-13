---
name: "slidev-theme-default"
description: "Generate Slidev presentations using the Default theme. Invoke when user wants to create general-purpose presentations with standard Slidev layouts and features."
---

# Slidev Default Theme

Generate presentations with Slidev using the Default theme.

## Available Layouts

| Layout | Description |
|--------|-------------|
| `center` | Center content on screen |
| `cover` | Cover page with title |
| `default` | Standard content layout |
| `end` | End page |
| `fact` | Highlight facts or data |
| `full` | Full screen content |
| `image-left` | Image on left, content on right |
| `image-right` | Image on right, content on left |
| `image` | Image as main content |
| `iframe-left` | Webpage on left, content on right |
| `iframe-right` | Webpage on right, content on left |
| `iframe` | Webpage as main content |
| `two-cols` | Two column layout |
| `two-cols-header` | Header with two columns below |

## Image Layout Usage

```yaml
---
layout: image-left
image: /path/to/image
class: my-custom-class
---
```

## Two Columns Layout

```yaml
---
layout: two-cols
---

# Left Column

Left content here

::right::

# Right Column

Right content here
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
