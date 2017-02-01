/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2017 Serge Rider (serge@jkiss.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jkiss.dbeaver.ui.editors.object.struct;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.jkiss.dbeaver.core.CoreMessages;
import org.jkiss.dbeaver.model.struct.rdb.DBSIndexType;
import org.jkiss.dbeaver.model.struct.rdb.DBSTable;
import org.jkiss.dbeaver.ui.UIUtils;
import org.jkiss.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * EditIndexPage
 *
 * @author Serge Rider
 */
public class EditIndexPage extends AttributesSelectorPage {

    private List<DBSIndexType> indexTypes;
    private DBSIndexType selectedIndexType;
    private boolean unique;

    public EditIndexPage(
        String title,
        DBSTable table,
        Collection<DBSIndexType> indexTypes)
    {
        super(title, table);
        this.indexTypes = new ArrayList<>(indexTypes);
        Assert.isTrue(!CommonUtils.isEmpty(this.indexTypes));
    }

    @Override
    protected void createContentsBeforeColumns(Composite panel)
    {
        UIUtils.createControlLabel(panel, CoreMessages.dialog_struct_edit_index_label_type);
        final Combo typeCombo = new Combo(panel, SWT.DROP_DOWN | SWT.READ_ONLY);
        typeCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        for (DBSIndexType indexType : indexTypes) {
            typeCombo.add(indexType.getName());
            if (selectedIndexType == null) {
                selectedIndexType = indexType;
            }
        }
        typeCombo.select(0);
        typeCombo.setEnabled(indexTypes.size() > 1);
        typeCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                selectedIndexType = indexTypes.get(typeCombo.getSelectionIndex());
            }
        });

        final Button uniqueButton = UIUtils.createLabelCheckbox(panel, "Unique", false);
        uniqueButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                unique = uniqueButton.getSelection();
            }
        });
    }

    public DBSIndexType getIndexType()
    {
        return selectedIndexType;
    }

    public boolean isUnique() {
        return unique;
    }
}
