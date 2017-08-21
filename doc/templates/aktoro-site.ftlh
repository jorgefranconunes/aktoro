<!DOCTYPE html>
<#--
  Template for regular pages in the Aktoro site.
-->
<html>

  <#include "aktoro-head.ftlh">

  <body>

    <div class="akt-body">

      <#include "aktoro-navbar.ftlh">

      <div class="container akt-content">

        <#include "aktoro-breadcrumbs.ftlh">

        <h1>${yawg.title}</h1>
        ${yawg.body?no_esc}

        <#include "aktoro-footer.ftlh">

      </div>

    </div>

  </body>

</html>
