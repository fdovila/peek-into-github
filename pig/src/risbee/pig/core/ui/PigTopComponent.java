/*
peek-into-github

Developed by Rishabh Rao
rishabhsrao.wordpress.com
rishabhsrao@gmail.com
twitter.com/rishabhsrao

This file is a part of peek-into-github.

peek-into-github is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

peek-into-github is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with peek-into-github. If not, see <http://www.gnu.org/licenses/>.
 */
package risbee.pig.core.ui;

import com.github.api.v2.services.GitHubException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.NbPreferences;
import org.openide.util.RequestProcessor;
import org.openide.util.RequestProcessor.Task;
import org.openide.util.TaskListener;
import risbee.pig.core.net.GithubRepos;
import risbee.pig.core.notification.ErrorNotifier;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//risbee.pig.core.ui//Pig//EN",
autostore = false)
@TopComponent.Description(preferredID = "PigTopComponent",
iconBase = "risbee/pig/core/res/bee_16.png",
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "rightSlidingSide", openAtStartup = false)
@ActionID(category = "Window", id = "risbee.pig.core.ui.PigTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_PigAction",
preferredID = "PigTopComponent")
public final class PigTopComponent extends TopComponent {

	public PigTopComponent() {
		initComponents();
		setName(NbBundle.getMessage(PigTopComponent.class, "CTL_PigTopComponent"));
		setToolTipText(NbBundle.getMessage(PigTopComponent.class, "HINT_PigTopComponent"));

		prefs = NbPreferences.forModule(PigPanel.class);

		this.refresh();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pigContainerPanel = new javax.swing.JPanel();
        treeTableSplitPane = new javax.swing.JSplitPane();
        treeScrollPane = new javax.swing.JScrollPane();
        pigTree = new javax.swing.JTree();
        tableScrollPane = new javax.swing.JScrollPane();
        pigTable = new javax.swing.JTable();
        refreshPigButton = new javax.swing.JButton();

        pigContainerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(PigTopComponent.class, "PigTopComponent.pigContainerPanel.border.title"))); // NOI18N
        pigContainerPanel.setLayout(new javax.swing.BoxLayout(pigContainerPanel, javax.swing.BoxLayout.Y_AXIS));

        treeTableSplitPane.setDividerLocation(125);
        treeTableSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        pigTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        treeScrollPane.setViewportView(pigTree);

        treeTableSplitPane.setTopComponent(treeScrollPane);

        pigTable.setAutoCreateRowSorter(true);
        pigTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        pigTable.setColumnSelectionAllowed(true);
        tableScrollPane.setViewportView(pigTable);
        pigTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        treeTableSplitPane.setRightComponent(tableScrollPane);

        pigContainerPanel.add(treeTableSplitPane);

        refreshPigButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/risbee/pig/core/res/refersh_16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(refreshPigButton, org.openide.util.NbBundle.getMessage(PigTopComponent.class, "PigTopComponent.refreshPigButton.text")); // NOI18N
        refreshPigButton.setToolTipText(org.openide.util.NbBundle.getMessage(PigTopComponent.class, "PigTopComponent.refreshPigButton.toolTipText")); // NOI18N
        refreshPigButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshPigButtonActionPerformed(evt);
            }
        });
        pigContainerPanel.add(refreshPigButton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pigContainerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pigContainerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void refreshPigButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshPigButtonActionPerformed
		// Disable the button till the refresh completes.
		this.refreshPigButton.setEnabled(false);
		this.refresh();
	}//GEN-LAST:event_refreshPigButtonActionPerformed
	/**
	 * The root of the tree; shows the username.
	 */
	private DefaultMutableTreeNode githubRootNode;
	/**
	 * The stored preferences.
	 */
	Preferences prefs;
	/**
	 * The background task.
	 */
	private Task githubConnectTask;
	/**
	 * The username of the Github user.
	 */
	private String githubUsername;
	/**
	 * The list of repositories.
	 */
	private GithubRepos repos;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pigContainerPanel;
    private javax.swing.JTable pigTable;
    private javax.swing.JTree pigTree;
    private javax.swing.JButton refreshPigButton;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JScrollPane treeScrollPane;
    private javax.swing.JSplitPane treeTableSplitPane;
    // End of variables declaration//GEN-END:variables

	@Override
	public void componentOpened() {
		// TODO add custom code on component opening
	}

	@Override
	public void componentClosed() {
		// TODO add custom code on component closing
	}

	void writeProperties(java.util.Properties p) {
		// better to version settings since initial version as advocated at
		// http://wiki.apidesign.org/wiki/PropertyFiles
		p.setProperty("version", "1.0");
		// TODO store your settings
	}

	void readProperties(java.util.Properties p) {
		String version = p.getProperty("version");
		// TODO read your settings according to their version
	}

	/**
	 * Refreshes all the contents from Github.
	 */
	private void refresh() {

		// Get from preferences. Default to "github" as the username.
		githubUsername = prefs.get("githubUsername", "github");

		// Create this root node before creating the new thread in startFetching.
		githubRootNode = new DefaultMutableTreeNode(githubUsername);

		// Start the background task.
		this.startFetching();
	}

	/**
	 * Starts the network communication and fetching process.
	 */
	synchronized public void startFetching() {
		RequestProcessor requestProcessor = new RequestProcessor(PigTopComponent.class.getName(), 1, true);
		final ProgressHandle progressHandle = ProgressHandleFactory.createHandle(NbBundle.getBundle(PigTopComponent.class).getString("progressText"));

		Runnable runnable = new Runnable() {

			@Override
			public void run() {				
				try {
					// Try to fetch repos; report any errors via bubble notification
					repos = new GithubRepos(githubUsername);
				} catch (GitHubException ex) {
					new ErrorNotifier().show(ex);
				}
			}
		};

		// Create a new task using the runnable.
		githubConnectTask = requestProcessor.create(runnable);

		// Add a task listener to keep track of the progress.
		githubConnectTask.addTaskListener(new TaskListener() {

			@Override
			public void taskFinished(org.openide.util.Task task) {
				try {
					// Stop showing the progress handle.
					progressHandle.finish();

					// In case the repos cannot be loaded, this will be null
					// thus will cause a null pointer exception.
					if(repos != null) {
						// Get the newly acquired tree.
						githubRootNode.add(repos.getTree());
					} else {
						// In case the repos cannot be fetched, then remove
						// the previous children.
						githubRootNode.removeAllChildren();
					}

					// Update JTree UI.
					pigTree.setModel(new DefaultTreeModel(githubRootNode));

					// Enable the button again.
					refreshPigButton.setEnabled(true);
				} catch(Exception ex) {
					new ErrorNotifier().show(ex);
				}
			}
		});

		// Start showing the progress handle.
		progressHandle.start();

		// Schedule and start the task with zero/no delay.
		githubConnectTask.schedule(0);
	}
}
