<head-bottom>
  <link rel="stylesheet" href="{{baseUrl}}/stylesheets/main.css">
</head-bottom>

<header sticky>
  <navbar type="dark">
    <a slot="brand" href="{{baseUrl}}/index.html" title="Home" class="navbar-brand">B2B4U</a>
    <li><a href="{{baseUrl}}/index.html" class="nav-link">Home</a></li>
    <li><a href="{{baseUrl}}/user-guide/index.html" class="nav-link">User Guide</a></li>
    <li><a href="{{baseUrl}}/DeveloperGuide.html" class="nav-link">Developer Guide</a></li>
    <li><a href="{{baseUrl}}/AboutUs.html" class="nav-link">About Us</a></li>
    <li><a href="https://github.com/AY2526S2-CS2103T-T08-1/tp" target="_blank" class="nav-link"><md>:fab-github:</md></a>
    </li>
    <li slot="right">
      <form class="navbar-form">
        <searchbar :data="searchData" placeholder="Search" :on-hit="searchCallback" menu-align-right></searchbar>
      </form>
    </li>
  </navbar>
</header>

<div id="flex-body">
  <nav id="site-nav">
    <div class="site-nav-top">
      <div class="fw-bold mb-2" style="font-size: 1.25rem;">Site Map</div>
    </div>
    <div class="nav-component slim-scroll">
      <site-nav>
* [Home]({{ baseUrl }}/index.html)
* [User Guide]({{ baseUrl }}/user-guide/index.html) :expanded:
  * [Quick Start]({{ baseUrl }}/user-guide/quick-start.html)
  * [Features]({{ baseUrl }}/user-guide/features.html)
  * [Commands]({{ baseUrl }}/user-guide/commands/index.html)
    * [Viewing Help]({{ baseUrl }}/user-guide/commands/view-help.html)
    * [Adding a Contact]({{ baseUrl }}/user-guide/commands/add-contact.html)
    * [Editing a Contact]({{ baseUrl }}/user-guide/commands/edit-contact.html)
    * [Deleting a Contact]({{ baseUrl }}/user-guide/commands/delete-contact.html)
    * [Clearing Contacts]({{ baseUrl }}/user-guide/commands/clear-contacts.html)
    * [Listing Contacts]({{ baseUrl }}/user-guide/commands/list-contacts.html)
    * [Finding Contacts]({{ baseUrl }}/user-guide/commands/find-contacts.html)
    * [Sorting Contacts]({{ baseUrl }}/user-guide/commands/sort-contacts.html)
    * [Managing Notes]({{ baseUrl }}/user-guide/commands/notes.html)
    * [Undoing a Command]({{ baseUrl }}/user-guide/commands/undo-command.html)
    * [Redoing a Command]({{ baseUrl }}/user-guide/commands/redo-command.html)
    * [Viewing Contact Details]({{ baseUrl }}/user-guide/commands/view-contact.html)
    * [Closing the Contact View]({{ baseUrl }}/user-guide/commands/close-view-contact.html)
    * [Exiting the Program]({{ baseUrl }}/user-guide/commands/exit-program.html)
  * [FAQ & Known Issues]({{ baseUrl }}/user-guide/faq-known-issues.html)
* [Developer Guide]({{ baseUrl }}/DeveloperGuide.html) :expanded:
  * [Acknowledgements]({{ baseUrl }}/DeveloperGuide.html#acknowledgements)
  * [Setting Up]({{ baseUrl }}/DeveloperGuide.html#setting-up-getting-started)
  * [Design]({{ baseUrl }}/DeveloperGuide.html#design)
  * [Implementation]({{ baseUrl }}/DeveloperGuide.html#implementation)
  * [Documentation, logging, testing, configuration, dev-ops]({{ baseUrl }}/DeveloperGuide.html#documentation-logging-testing-configuration-dev-ops)
  * [Appendix: Requirements]({{ baseUrl }}/DeveloperGuide.html#appendix-requirements)
  * [Appendix: Instructions for manual testing]({{ baseUrl }}/DeveloperGuide.html#appendix-instructions-for-manual-testing)
* [About Us]({{ baseUrl }}/AboutUs.html)
      </site-nav>
    </div>
  </nav>
  <div id="content-wrapper">
    {{ content }}
  </div>
  <nav id="page-nav">
    <div class="nav-component slim-scroll">
      <page-nav />
    </div>
  </nav>
  <scroll-top-button></scroll-top-button>
</div>

<footer>
  <!-- Support MarkBind by including a link to us on your landing page! -->
  <div class="text-center">
    <small>[<md>**Powered by**</md> <img src="https://markbind.org/favicon.ico" width="30"> {{MarkBind}}, generated on {{timestamp}}]</small>
  </div>
</footer>
