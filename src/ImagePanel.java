import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fid.Fiv;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 5;
	private BufferedImage m_image;

	public void showImage(Fid image) {
		Fiv view = image.getViews()[0];
		m_image = new BufferedImage(view.getWidth(), view.getHeight(),
				BufferedImage.TYPE_BYTE_GRAY);
		m_image.getRaster().setDataElements(0, 0, view.getWidth(),
				view.getHeight(), view.getImageData());
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(m_image, 0, 0, null);
	}

}
