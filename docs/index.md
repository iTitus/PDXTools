---
permalink: "/"
layout: default
---

## The Clausewitz Engine Scripting Language

{% for repository in site.github.public_repositories %}
* [{{ repository.name }}]({{ repository.html_url }})
{% endfor %}
