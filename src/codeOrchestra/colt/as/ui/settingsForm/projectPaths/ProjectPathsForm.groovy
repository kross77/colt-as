package codeOrchestra.colt.as.ui.settingsForm.projectPaths

import codeOrchestra.colt.as.model.AsProjectPaths
import codeOrchestra.colt.as.model.ModelStorage
import codeOrchestra.colt.as.model.beans.BuildModel
import codeOrchestra.colt.as.ui.settingsForm.IFormValidated
import codeOrchestra.colt.core.ui.components.fileset.FilesetInput
import codeOrchestra.colt.core.ui.components.inputForms.FormType
import codeOrchestra.colt.core.ui.components.inputForms.LTBForm
import codeOrchestra.colt.core.ui.components.inputForms.group.FormGroup
import codeOrchestra.colt.core.ui.components.inputFormsNew.LActionFrom
import codeOrchestra.colt.core.ui.components.inputFormsNew.group.FormGroupNew
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.geometry.Insets
import javafx.scene.Parent
import javafx.stage.FileChooser

/**
 * @author Dima Kruk
 */
class ProjectPathsForm extends FormGroupNew implements IFormValidated {
    private FilesetInput sources
    private FilesetInput libraries
    private FilesetInput assets

    private LActionFrom mainClass

    private AsProjectPaths model = ModelStorage.instance.project.projectPaths
    private BuildModel buildModel = ModelStorage.instance.project.projectBuildSettings.buildModel

    ProjectPathsForm() {
        sources = new FilesetInput(title: "Source Paths:", useFiles: false, useExcludes: false)
        libraries = new FilesetInput(title: "Library Paths:", useExcludes: false)
        assets = new FilesetInput(title: "Assets Paths:", useFiles: false, useExcludes: false)

        mainClass = new LActionFrom(title: "Main class:")

        children.addAll(sources, libraries, assets, mainClass)

        init()
    }

    public void init() {
        mainClass.extensionFilters.addAll(new FileChooser.ExtensionFilter("Class", "*.as", "*.mxml"))

        mainClass.text().addListener({ ObservableValue<? extends String> observableValue, String t, String t1 ->
            if (t1) {
                File file = new File(t1)
                if (file.exists() && file.isFile()) {
                    if (buildModel.outputFileName.isEmpty()) {
                        buildModel.outputFileName = file.name.replaceAll(/\.(as|mxml)$/, ".swf")
                    }
                }
            }
        } as ChangeListener)

        mainClass.activateValidation()

        bindModel()
    }

    void bindModel() {
        mainClass.text().bindBidirectional(buildModel.mainClass())

        sources.files().bindBidirectional(model.sources())
        libraries.files().bindBidirectional(model.libraries())
        assets.files().bindBidirectional(model.assets())
    }

    @Override
    Parent validated() {
        return mainClass.validateValue() ? mainClass : null
    }
}
