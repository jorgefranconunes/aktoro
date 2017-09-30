<!DOCTYPE html>
<#--
  Template for pages with no title in the Aktoro site.

  This is actually very similar to the "aktoro-site.ftlh" template, only
  without the H1 title. This is intended to be used for HTML documents
  that render the page title in their own way. One example is the site
  home page.
-->

<html>

  <#include "aktoro-head.ftlh">

  <body>

    <div class="akt-body">

      <#include "aktoro-navbar.ftlh">

      <div class="container akt-content">

        <#include "aktoro-breadcrumbs.ftlh">

        ${yawg.body?no_esc}

        <#include "aktoro-footer.ftlh">

      </div>

    </div>

  </body>

</html>
