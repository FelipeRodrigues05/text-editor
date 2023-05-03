import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

@SuppressWarnings("serial")
public class Screen extends JFrame {
	private int width = 500;
	private int height = 500;
	private int fontSize;
	private JTextPane text;
	private FileDialog fdOpen, fdSave;
	private String nomeArquivo, ScreenTitle;

	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu, editMenu, alignmentMenu, stylesMenu, fontSizeMenu, fontsMenu, fontColorsMenu;
	private JMenuItem saveAction, openAction, clearAction, exitAction, boldFontAction, italicFontAction, Font8Action,
			Font15Action, Font24Action, AbyssinicaFontAction, AniFontAction, ChilankaFontAction,
			justifiedAlignmentAction, centralizedAlignmentAction, rightAlignmentAction, redFontAction, purpleFontAction,
			blackFontAction;

	public Screen() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}

		initializeComponents();
		Events();
	}

	public void initializeComponents() {
		ScreenTitle = "Editor de Texto";
		setTitle(ScreenTitle);
		setJMenuBar(menuBar);
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(width, height);
		setVisible(true);

		text = new JTextPane();
		text.setSize(width, height);
		text.setFont(new Font("", Font.PLAIN, 12));
		fontSize = 12;

		fdOpen = new FileDialog(this, "Abrir Arquivo", FileDialog.LOAD);
		fdSave = new FileDialog(this, "Salvar Arquivo", FileDialog.SAVE);

		fileMenu = new JMenu("Arquivo");
		editMenu = new JMenu("Editor");
		alignmentMenu = new JMenu("Alinhamento");

		saveAction = new JMenuItem("Salvar Arquivo");
		saveAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		openAction = new JMenuItem("Abrir Arquivo");
		openAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		clearAction = new JMenuItem("Limpar Janela");
		clearAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		exitAction = new JMenuItem("Sair");
		exitAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));

		stylesMenu = new JMenu("Estilos Fonte");
		boldFontAction = new JMenuItem("Negrito");
		boldFontAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
		italicFontAction = new JMenuItem("Itálico");
		italicFontAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

		fontSizeMenu = new JMenu("Tamanho Fontes");
		Font8Action = new JMenuItem("12px");
		Font15Action = new JMenuItem("15px");
		Font24Action = new JMenuItem("24px");
		
		fontsMenu = new JMenu("Fontes");
		AbyssinicaFontAction = new JMenuItem("Abyssinica");
		AniFontAction = new JMenuItem("Ani");
		ChilankaFontAction = new JMenuItem("Chilanka");

		justifiedAlignmentAction = new JMenuItem("Justificado");
		centralizedAlignmentAction = new JMenuItem("Centralizado");
		rightAlignmentAction = new JMenuItem("Á Direita");

		fontColorsMenu = new JMenu("Cores");
		redFontAction = new JMenuItem("Vermelho");
		purpleFontAction = new JMenuItem("Roxo");
		blackFontAction = new JMenuItem("Preto");
		
		stylesMenu.add(boldFontAction);
		stylesMenu.add(italicFontAction);
		
		fontSizeMenu.add(Font8Action);
		fontSizeMenu.add(Font15Action);
		fontSizeMenu.add(Font24Action);
		
		fontsMenu.add(AbyssinicaFontAction);
		fontsMenu.add(AniFontAction);
		fontsMenu.add(ChilankaFontAction);
		
		fontColorsMenu.add(redFontAction);
		fontColorsMenu.add(purpleFontAction);
		fontColorsMenu.add(blackFontAction);

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		
		alignmentMenu.add(justifiedAlignmentAction);
		alignmentMenu.add(centralizedAlignmentAction);
		alignmentMenu.add(rightAlignmentAction);
		
		fileMenu.add(saveAction);
		fileMenu.add(openAction);
		fileMenu.add(clearAction);
		fileMenu.addSeparator();
		fileMenu.add(exitAction);

		editMenu.add(stylesMenu);
		editMenu.add(fontsMenu);
		editMenu.add(fontSizeMenu);
		editMenu.add(fontColorsMenu);
		editMenu.addSeparator();
		editMenu.add(alignmentMenu);

		add(text);

	}

	public void Events() {
		exitAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		saveAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					fdSave.setVisible(true);
					if (fdSave.getFile() == null) {
						return;
					}
					fileName = fdSave.getDirectory() + fdSave.getFile();
					FileWriter out = new FileWriter(fileName + ".txt");
					out.write(text.getText());
					out.close();
					setTitle("Arquivo salvo com sucesso!");
					try {
						Thread.sleep(2500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					setTitle("Editor de Texto");
				} catch (IOException erro) {
					JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + erro.toString(),
							"Erro ao salvar o arquivo", JOptionPane.ERROR_MESSAGE);

				}
			}
		});

		openAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fdOpen.setVisible(true);
					if (fdOpen.getFile() == null) {
						return;
					}
					fileName = fdOpen.getDirectory() + fdOpen.getFile();
					FileReader in = new FileReader(fileName);
					String s = "";
					int i = in.read();
					while (i != -1) {
						s = s + (char) i;
						i = in.read();
					}
					text.setText(s);
					in.close();

					setTitle(fileName + " aberto com sucesso!");
					try {
						Thread.sleep(2500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					setTitle("Editor de Texto");
				} catch (IOException erro) {
					JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + erro.toString(),
							"Erro ao salvar o arquivo", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		clearAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				text.setText(" ");

			}
		});

		Font8Action.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				text.setFont(new Font("", Font.PLAIN, 8));
				fontSize = 8;

			}
		});

		Font15Action.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				text.setFont(new Font("", Font.PLAIN, 15));
				fontSize = 15;
			}
		});

		Font24Action.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				text.setFont(new Font("", Font.PLAIN, 24));
				fontSize = 24;
			}
		});

		boldFontAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				text.setFont(new Font("", Font.BOLD, fontSize));

			}
		});

		italicFontAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				text.setFont(new Font("", Font.ITALIC, fontSize));

			}
		});

		AbyssinicaFontAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				text.setFont(new Font("Abyssinica SIL", Font.PLAIN, fontSize));

			}
		});

		AniFontAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				text.setFont(new Font("Ani", Font.PLAIN, fontSize));
			}
		});

		ChilankaFontAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				text.setFont(new Font("Chilanka", Font.PLAIN, fontSize));
			}
		});

		justifiedAlignmentAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StyledDocument doc = texto.getStyledDocument();
				SimpleAttributeSet center = new SimpleAttributeSet();
				StyleConstants.setAlignment(center, StyleConstants.ALIGN_JUSTIFIED);
				doc.setParagraphAttributes(0, doc.getLength(), center, false);

			}
		});

		centralizedAlignmentAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StyledDocument doc = texto.getStyledDocument();
				SimpleAttributeSet center = new SimpleAttributeSet();
				StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
				doc.setParagraphAttributes(0, doc.getLength(), center, false);

			}
		});

		rightAlignmentAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StyledDocument doc = texto.getStyledDocument();
				SimpleAttributeSet center = new SimpleAttributeSet();
				StyleConstants.setAlignment(center, StyleConstants.ALIGN_RIGHT);
				doc.setParagraphAttributes(0, doc.getLength(), center, false);

			}
		});

		redFontAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				text.setForeground(Color.red);
			}
		});

		purpleFontAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				text.setForeground(new Color(111, 0, 255));

			}
		});

		blackFontAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				text.setForeground(Color.black);

			}
		});
	}
}
