---
name: "slidev-theme-frankfurt"
description: "Generate Slidev presentations using the Frankfurt theme. Invoke when user wants to create presentations with section indicators and progress tracking on top."
---

# Slidev Frankfurt Theme

Generate presentations with Slidev using the Frankfurt theme, featuring section indicators and progress tracking.

## Key Feature: Sections

The Frankfurt theme displays section indicators at the top of slides. Use the `section` field in frontmatter:

```yaml
---
layout: default
section: 'Introduction'
---
```

## Available Layouts

| Layout | Description |
|--------|-------------|
| `center` | Center content |
| `cover` | Cover page |
| `default` | Standard layout |
| `end` | End page |
| `fact` | Highlight facts |
| `full` | Full screen |
| `image-left` | Image left, content right |
| `image-right` | Image right, content left |
| `image` | Full image |
| `two-cols` | Two columns |
| `two-cols-header` | Header + two columns |

## Section Usage

```yaml
---
layout: default
section: 'Chapter 1: Getting Started'
---

# Welcome

This slide will show 'Chapter 1: Getting Started' in the progress bar.
```

## Progress Tracking

The theme automatically shows:
- Current section name
- Progress through presentation
- Navigation indicators

## Reference Files

- `outline_generate.md` - Generate presentation outline
- `slidev_generate.md` - Base slide generation
- `slidev_generate_with_specific_outlines.md` - Generate with specific outlines
- `user_info.md` - User information template
- `extension.md` - Extension commands
- `slidev_animation.md` - Animation syntax
- `slidev_layouts.md` - Layout definitions
