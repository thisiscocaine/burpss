package burp;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class BurpExtender implements IBurpExtender, ITab {

    // --- Configuration ---
    private static final int CORNER_RADIUS = 30; 
    private static final File SAVE_DIR = new File(System.getProperty("user.home"), "burpss");

    // --- Globals ---
    private IBurpExtenderCallbacks callbacks;
    private PrintWriter stdout;
    private JPanel mainPanel;
    private JTextArea logArea;
    private JButton btnScreenshot;
    
    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        this.stdout = new PrintWriter(callbacks.getStdout(), true);
        callbacks.setExtensionName("burpss");

        if (!SAVE_DIR.exists()) SAVE_DIR.mkdirs();

        SwingUtilities.invokeLater(this::initUI);
    }

    // --- UI Construction ---
    private void initUI() {
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel title = new JLabel("Burp Screenshot");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        mainPanel.add(title, c);

        // Screenshot Button
        btnScreenshot = new JButton("Screenshot (10s Delay)");
        btnScreenshot.setBackground(new Color(255, 140, 0));
        btnScreenshot.setForeground(Color.BLACK);
        btnScreenshot.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnScreenshot.setPreferredSize(new Dimension(300, 50));
        btnScreenshot.addActionListener(e -> startScreenshotProcess());
        c.gridy = 1;
        mainPanel.add(btnScreenshot, c);

        // Log
        logArea = new JTextArea(8, 40);
        logArea.setEditable(false);
        c.gridy = 2;
        mainPanel.add(new JScrollPane(logArea), c);

        callbacks.customizeUiComponent(mainPanel);
        callbacks.addSuiteTab(this);
    }

    // --- Logic: Countdown & Routing ---
    private void startScreenshotProcess() {
        startCountdown(btnScreenshot, () -> {
            Rectangle area = resolveCaptureArea();
            if (area != null) captureScreenshot(area);
        });
    }

    private void startCountdown(JButton button, Runnable onComplete) {
        button.setEnabled(false);
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 10; i > 0; i--) {
                    publish("Switch Tab! " + i + "s");
                    Thread.sleep(1000);
                }
                return null;
            }
            @Override
            protected void process(List<String> chunks) {
                button.setText(chunks.get(chunks.size() - 1));
            }
            @Override
            protected void done() {
                if (button == btnScreenshot) button.setText("Screenshot (10s Delay)");
                
                button.setEnabled(true);
                onComplete.run();
            }
        };
        worker.execute();
    }

    private Rectangle resolveCaptureArea() {
        // Automatically detect Burp window bounds
        Window w = SwingUtilities.getWindowAncestor(mainPanel);
        if (w == null) return new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        return w.getBounds();
    }

    // --- SCREENSHOT ENGINE ---
    private void captureScreenshot(Rectangle bounds) {
        try {
            BufferedImage image = captureFrame(bounds);
            String filename = "burpss_" + getTimestamp() + ".png";
            File file = new File(SAVE_DIR, filename);
            ImageIO.write(image, "png", file);
            log("✅ Screenshot Saved: " + file.getAbsolutePath());
            Toolkit.getDefaultToolkit().beep();
        } catch (Exception e) {
            log("❌ Error: " + e.getMessage());
        }
    }

    // --- CORE IMAGE PROCESSING ---
    private BufferedImage captureFrame(Rectangle bounds) throws Exception {
        Robot robot = new Robot();
        BufferedImage raw = robot.createScreenCapture(bounds);
        // Cursor drawing removed
        return applyRoundedCorners(raw, CORNER_RADIUS);
    }

    private BufferedImage applyRoundedCorners(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();

        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return output;
    }

    private String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }

    private void log(String msg) {
        SwingUtilities.invokeLater(() -> {
            if (logArea != null) {
                logArea.append(msg + "\n");
                logArea.setCaretPosition(logArea.getDocument().getLength());
            }
        });
        stdout.println(msg);
    }

    @Override public String getTabCaption() { return "burpss"; }
    @Override public Component getUiComponent() { return mainPanel; }
}
