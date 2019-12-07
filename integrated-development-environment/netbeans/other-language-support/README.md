
Most of these instructions are from this guide:

    https://blogs.apache.org/netbeans/entry/lsp-client-demo-ba-sh

The grammar and lexers are from this repository:

    https://github.com/tbuser/openscad.tmbundle

Guidance for this guide comes from this Netbeans issue:

    https://issues.apache.org/jira/browse/NETBEANS-3418

----------------------------------------------------------------------

Installation instructions

    1) Download the .nbm file

    2) Tools -> Plugins -> Downloaded -> Add -> navivate to and select the .nbm file

----------------------------------------------------------------------

Remove/Uninstal a Netbeans Module
    
    rm ~/.netbeans/11.1/modules/org-onebeartoe-openscad.jar \ 
       ~/.netbeans/11.1/config/Modules/org-onebeartoe-openscad.xml \ 
       ~/.netbeans/11.1/update_tracking/org-onebeartoe-openscad.xml 


----------------------------------------------------------------------

Build instructions (mostly borrowed from insttuctions guide linked to above) for NetBeans IDE 11

    1) Create a NetBeans Module

            File -> New Project -> Java with ANT -> NetBeans Module -> Module

    2) Create a File Type 

            Right click project -> New File -> Module Development -> File Type

                mime type: text/plain
                file extension: scad

    3) Add 'TextMate Lexer' as a module dependency

            Right click the project -> Properties -> Libraries -> Module Dependecies tab -> Add -> 'TextMate Lexer'

    3) Syntax Coloring

          a) Place the 'OpenSCAD.tmLanguage' (found in the Github repository linked
             above) file alongside the ____DataObject.java file.

          b) Annotate ___DataObject.java with the following

                @GrammarRegistration(grammar="OpenSCAD.tmLanguage", mimeType="text/plain")

          c) include the import

                import org.netbeans.modules.textmate.lexer.api.GrammarRegistration;

    4) Right click the project -> 'Install/Reload in Development IDE'

    5) Restart the NetBeans IDE if the syntax coloring does not take effect immediately.

    
    
    
    
    
-------------------------------------------------------------

What didnt't work:

Schliemann
    this only worked in NetBeans verson 6.0.1   


	https://netbeans.org/community/magazine/html/03/schliemann/

	http://wiki.netbeans.org/Schliemann


	bonus:

		https://platform.netbeans.org/tutorials/60/nbm-prolog.html



Maven Support
The following was not available in a public Maven repository:

    org.netbeans.modules.textmate.lexer.api.GrammarRegistrations
