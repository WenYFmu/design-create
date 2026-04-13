---
name: "user_info"
description: "User information template for vuetiful theme"
---

# user_info

## Content

``xml
<USER_INFO>
<NAME value="{{ username | default('') }}" />
<EMAIL value="{{ email | default('') }}" />
<WEBSITE value="{{ website | default('') }}" />
</USER_INFO>
``