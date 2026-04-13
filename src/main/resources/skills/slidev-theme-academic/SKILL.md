---
name: "slidev-theme-academic"
description: "Generate Slidev presentations using the Academic theme. Invoke when user wants to create academic/technical presentations with figure layouts, table of contents, and bibliography support."
---

# Slidev Academic Theme

Generate professional academic presentations with Slidev using the Academic theme.

## API Endpoints

Use these endpoints to generate presentations:

### 1. Generate Outline

**Prompt File:** `t/academic/outline_generate.txt`

**Purpose:** Generate a structured presentation outline from content.

**Request:**
```json
{
  "title": "Presentation Title",
  "content": "User input content or URL"
}
```

**Features:**
- Generates 5-10 main sections
- Creates clear titles and descriptions
- Supports web search for URLs
- Outputs structured outline in JSON format

### 2. Generate Slides

**Prompt File:** `t/academic/slidev_generate_with_specific_outlines.txt`

**Purpose:** Generate complete Slidev markdown with specific outlines.

**Request:**
```json
{
  "title": "Presentation Title",
  "content": "Source content",
  "outlines": [...],
  "path": "/save/path"
}
```

## Available Layouts

| Layout | Description | Use Case |
|--------|-------------|----------|
| `cover` | Cover page with author info | Title slide |
| `default` | Standard content layout | Regular slides |
| `figure` | Figure with caption | Images with descriptions |
| `figure-side` | Side figure layout | Text + image side by side |
| `table-of-contents` | Auto-generated TOC | Outline/overview |
| `index` | Bibliography/references | References page |
| `two-cols` | Two column layout | Comparative content |

## Cover Layout Parameters

```yaml
---
layout: cover
coverAuthor: "Author Name"
coverAuthorUrl: "https://author.com"
coverBackgroundUrl: "./bg.jpg"
coverBackgroundSource: "Image Source"
coverBackgroundSourceUrl: "https://source.com"
coverDate: "2024-01-01"
---
```

## Figure Layout Parameters

```yaml
---
layout: figure
figureUrl: "./image.png"
figureCaption: "Figure description"
figureFootnoteNumber: 1
---
```

## Figure-Side Layout Parameters

```yaml
---
layout: figure-side
figureUrl: "./image.png"
figureCaption: "Description"
figureX: "r"  # 'l' for left, 'r' for right
---
```

## Animation Syntax

```html
<!-- Click to reveal -->
<v-click>Hidden content</v-click>

<!-- Show with previous -->
<div v-after>Appears together</div>

<!-- Hide after click -->
<div v-click.hide>Disappears next</div>
```

## Extension Commands

- `:sum {{url}}` - Web search and summarize
- `:mermaid {{description}}` - Generate mermaid diagram

## Best Practices

1. Always generate outline first
2. Use `figure` or `figure-side` for images (not markdown syntax)
3. Keep each slide under 12 lines per side
4. Split pages with multiple H2 headings
5. Use `index` layout for references/bibliography
6. Call `slidev_export_project` after completion

## Example Workflow

1. Call outline generation API with title and content
2. Review and adjust the generated outline
3. Call slide generation API with outline, title, content, and path
4. Export the project using `slidev_export_project`
