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
	private JTextPane texto;
	private FileDialog fdAbrir, fdSalvar;
	private String nomeArquivo, ScreenTitle;

	private JMenuBar menuBar = new JMenuBar();
	private JMenu arquivoMenu, editarMenu, alinhamentoMenu, estilosMenu, tamanhoFontesMenu, fontesMenu, coresFonteMenu;
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

		texto = new JTextPane();
		texto.setSize(width, height);
		texto.setFont(new Font("", Font.PLAIN, 12));
		fontSize = 12;

		fdAbrir = new FileDialog(this, "Abrir Arquivo", FileDialog.LOAD);
		fdSalvar = new FileDialog(this, "Salvar Arquivo", FileDialog.SAVE);

		arquivoMenu = new JMenu("Arquivo");
		editarMenu = new JMenu("Editor");
		alinhamentoMenu = new JMenu("Alinhamento");
		menuBar.add(arquivoMenu);
		menuBar.add(editarMenu);

		saveAction = new JMenuItem("Salvar Arquivo");
		saveAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		openAction = new JMenuItem("Abrir Arquivo");
		openAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		clearAction = new JMenuItem("Limpar Janela");
		clearAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		exitAction = new JMenuItem("Sair");
		exitAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));

		estilosMenu = new JMenu("Estilos Fonte");
		boldFontAction = new JMenuItem("Negrito");
		boldFontAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
		italicFontAction = new JMenuItem("Itálico");
		italicFontAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		estilosMenu.add(boldFontAction);
		estilosMenu.add(italicFontAction);

		tamanhoFontesMenu = new JMenu("Tamanho Fontes");
		Font8Action = new JMenuItem("12px");
		Font15Action = new JMenuItem("15px");
		Font24Action = new JMenuItem("24px");
		tamanhoFontesMenu.add(Font8Action);
		tamanhoFontesMenu.add(Font15Action);
		tamanhoFontesMenu.add(Font24Action);

		fontesMenu = new JMenu("Fontes");
		AbyssinicaFontAction = new JMenuItem("Abyssinica");
		AniFontAction = new JMenuItem("Ani");
		ChilankaFontAction = new JMenuItem("Chilanka");
		fontesMenu.add(AbyssinicaFontAction);
		fontesMenu.add(AniFontAction);
		fontesMenu.add(ChilankaFontAction);

		justifiedAlignmentAction = new JMenuItem("Justificado");
		centralizedAlignmentAction = new JMenuItem("Centralizado");
		rightAlignmentAction = new JMenuItem("Á Direita");
		alinhamentoMenu.add(justifiedAlignmentAction);
		alinhamentoMenu.add(centralizedAlignmentAction);
		alinhamentoMenu.add(rightAlignmentAction);

		coresFonteMenu = new JMenu("Cores");
		redFontAction = new JMenuItem("Vermelho");
		purpleFontAction = new JMenuItem("Roxo");
		blackFontAction = new JMenuItem("Preto");
		coresFonteMenu.add(redFontAction);
		coresFonteMenu.add(purpleFontAction);
		coresFonteMenu.add(blackFontAction);

		arquivoMenu.add(saveAction);
		arquivoMenu.add(openAction);
		arquivoMenu.add(clearAction);
		arquivoMenu.addSeparator();
		arquivoMenu.add(exitAction);

		editarMenu.add(estilosMenu);
		editarMenu.add(fontesMenu);
		editarMenu.add(tamanhoFontesMenu);
		editarMenu.add(coresFonteMenu);
		editarMenu.addSeparator();
		editarMenu.add(alinhamentoMenu);

		add(texto);

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
					fdSalvar.setVisible(true);
					if (fdSalvar.getFile() == null) {
						return;
					}
					nomeArquivo = fdSalvar.getDirectory() + fdSalvar.getFile();
					FileWriter out = new FileWriter(nomeArquivo + ".txt");
					out.write(texto.getText());
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
					fdAbrir.setVisible(true);
					if (fdAbrir.getFile() == null) {
						return;
					}
					nomeArquivo = fdAbrir.getDirectory() + fdAbrir.getFile();
					FileReader in = new FileReader(nomeArquivo);
					String s = "";
					int i = in.read();
					while (i != -1) {
						s = s + (char) i;
						i = in.read();
					}
					texto.setText(s);
					in.close();

					setTitle(nomeArquivo + " aberto com sucesso!");
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
				texto.setText(" ");

			}
		});

		Font8Action.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				texto.setFont(new Font("", Font.PLAIN, 8));
				fontSize = 8;

			}
		});

		Font15Action.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				texto.setFont(new Font("", Font.PLAIN, 15));
				fontSize = 15;
			}
		});

		Font24Action.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				texto.setFont(new Font("", Font.PLAIN, 24));
				fontSize = 24;
			}
		});

		boldFontAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				texto.setFont(new Font("", Font.BOLD, fontSize));

			}
		});

		italicFontAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				texto.setFont(new Font("", Font.ITALIC, fontSize));

			}
		});

		AbyssinicaFontAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				texto.setFont(new Font("Abyssinica SIL", Font.PLAIN, fontSize));

			}
		});

		AniFontAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				texto.setFont(new Font("Ani", Font.PLAIN, fontSize));
			}
		});

		ChilankaFontAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				texto.setFont(new Font("Chilanka", Font.PLAIN, fontSize));
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
				texto.setForeground(Color.red);

			}
		});

		purpleFontAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				texto.setForeground(new Color(111, 0, 255));

			}
		});

		blackFontAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				texto.setForeground(Color.black);

			}
		});
	}
}
