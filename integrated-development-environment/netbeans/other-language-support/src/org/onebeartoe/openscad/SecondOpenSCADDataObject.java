/*
 */
package org.onebeartoe.openscad;

import java.io.IOException;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.netbeans.modules.textmate.lexer.api.GrammarRegistration;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

import org.netbeans.modules.textmate.lexer.api.GrammarRegistration;

@GrammarRegistration(grammar="OpenSCAD.tmLanguage", mimeType="text/plain")
@GrammarRegistration(grammar="cube.tmSnippet", mimeType="text/plain")
@Messages(
{
    "LBL_SecondOpenSCAD_LOADER=Files of SecondOpenSCAD"
})
@MIMEResolver.ExtensionRegistration(
        displayName = "#LBL_SecondOpenSCAD_LOADER",
        mimeType = "text/plain",
        extension =
        {
            ".scad"
        }
)
@DataObject.Registration(
        mimeType = "text/plain",
        iconBase = "org/openscad/openscad-icon-16.png",
        displayName = "#LBL_SecondOpenSCAD_LOADER",
        position = 300
)
@ActionReferences(
{
    @ActionReference(
            path = "Loaders/text/plain/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
            position = 100,
            separatorAfter = 200
    ),
    @ActionReference(
            path = "Loaders/text/plain/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
            position = 300
    ),
    @ActionReference(
            path = "Loaders/text/plain/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
            position = 400,
            separatorAfter = 500
    ),
    @ActionReference(
            path = "Loaders/text/plain/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
            position = 600
    ),
    @ActionReference(
            path = "Loaders/text/plain/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
            position = 700,
            separatorAfter = 800
    ),
    @ActionReference(
            path = "Loaders/text/plain/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
            position = 900,
            separatorAfter = 1000
    ),
    @ActionReference(
            path = "Loaders/text/plain/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
            position = 1100,
            separatorAfter = 1200
    ),
    @ActionReference(
            path = "Loaders/text/plain/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
            position = 1300
    ),
    @ActionReference(
            path = "Loaders/text/plain/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
            position = 1400
    )
})
public class SecondOpenSCADDataObject extends MultiDataObject
{

    public SecondOpenSCADDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException
    {
        super(pf, loader);
        registerEditor("text/plain", true);
    }

    @Override
    protected int associateLookup()
    {
        return 1;
    }

    @MultiViewElement.Registration(
            displayName = "#LBL_SecondOpenSCAD_EDITOR",
            iconBase = "org/openscad/openscad-icon-16.png",
            mimeType = "text/plain",
            persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
            preferredID = "SecondOpenSCAD",
            position = 1000
    )
    @Messages("LBL_SecondOpenSCAD_EDITOR=Source")
    public static MultiViewEditorElement createEditor(Lookup lkp)
    {
        return new MultiViewEditorElement(lkp);
    }

}
