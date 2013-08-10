import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.ReaderCollection;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.UareUGlobal;

public class UareUSampleJava extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1;

	private static final String ACT_SELECTION = "selection";
	private static final String ACT_CAPTURE = "capture";
	private static final String ACT_STREAMING = "streaming";
	private static final String ACT_VERIFICATION = "verification";
	private static final String ACT_IDENTIFICATION = "identification";
	private static final String ACT_ENROLLMENT = "enrollment";
	private static final String ACT_EXIT = "exit";

	private JDialog m_dlgParent;
	private JTextArea m_textReader;

	private static ReaderCollection m_collection;
	private static Reader m_reader;
	private static Fmd enrollmentFMD;

	private UareUSampleJava() {
		final int vgap = 5;
		final int width = 380;

		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);

		JLabel lblReader = new JLabel(" ");

		Dimension dm = lblReader.getPreferredSize();
		dm.width = width;
		lblReader.setPreferredSize(dm);
		add(lblReader);

		JButton btnEnrollment = new JButton("Run enrollment");
		btnEnrollment.setActionCommand(ACT_ENROLLMENT);
		btnEnrollment.addActionListener(this);
		add(btnEnrollment);
		add(Box.createVerticalStrut(vgap));

		JButton btnVerification = new JButton("Run verification");
		btnVerification.setActionCommand(ACT_VERIFICATION);
		btnVerification.addActionListener(this);
		add(btnVerification);
		add(Box.createVerticalStrut(vgap));

		add(Box.createVerticalStrut(vgap));
		JButton btnExit = new JButton("Exit");
		btnExit.setActionCommand(ACT_EXIT);
		btnExit.addActionListener(this);
		add(btnExit);
		add(Box.createVerticalStrut(vgap));

		setOpaque(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals(ACT_VERIFICATION)) {

			try {
				this.m_collection = UareUGlobal.GetReaderCollection();
				m_collection.GetReaders();
			} catch (UareUException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Error getting collection");
				return;
			}

			if (m_collection.size() == 0) {
				MessageBox.Warning("Reader is not selected");
				return;
			}

			m_reader = m_collection.get(0);

			if (null == m_reader) {
				MessageBox.Warning("Reader is not selected");
			} else {
				Verification.Run(m_reader, this.enrollmentFMD);
			}
		}

		else if (e.getActionCommand().equals(ACT_ENROLLMENT)) {

			try {
				this.m_collection = UareUGlobal.GetReaderCollection();
				m_collection.GetReaders();
			} catch (UareUException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Error getting collection");
				return;
			}

			if (m_collection.size() == 0) {
				MessageBox.Warning("Reader is not selected");
				return;
			}

			m_reader = m_collection.get(0);

			if (null == m_reader) {
				MessageBox.Warning("Reader is not selected");
			} else {

				this.enrollmentFMD = Enrollment.Run(m_reader);
			}
		} else if (e.getActionCommand().equals(ACT_EXIT)) {
			m_dlgParent.setVisible(false);
		}
	}

	private void doModal(JDialog dlgParent) {
		m_dlgParent = dlgParent;
		m_dlgParent.setContentPane(this);
		m_dlgParent.pack();
		m_dlgParent.setLocationRelativeTo(null);
		m_dlgParent.setVisible(true);
		m_dlgParent.dispose();
	}

	private static void createAndShowGUI() {
		UareUSampleJava paneContent = new UareUSampleJava();

		// initialize capture library by acquiring reader collection
		try {
			paneContent.m_collection = UareUGlobal.GetReaderCollection();
		} catch (UareUException e) {
			MessageBox.DpError("UareUGlobal.getReaderCollection()", e);
			return;
		}

		// run dialog
		JDialog dlg = new JDialog((JDialog) null,
				"Finger Print Scanning @Android Ankit ", true);
		paneContent.doModal(dlg);

		// release capture library by destroying reader collection
		try {
			UareUGlobal.DestroyReaderCollection();
		} catch (UareUException e) {
			MessageBox.DpError("UareUGlobal.destroyReaderCollection()", e);
		}
	}

	public static void main(String[] args) throws IOException {
		// SwingUtilities.invokeLater(new WebcamViewerExample());
		// createAndShowGUI();
		try {
			m_collection = UareUGlobal.GetReaderCollection();
			m_collection.GetReaders();
		} catch (UareUException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error getting collection");
			return;
		}

		m_reader = m_collection.get(0);

		enrollmentFMD = Enrollment.Run(m_reader);

	}

}
