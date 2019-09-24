package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Light;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GateView extends FixedPanel implements ActionListener, MouseListener {
    private final Switch[] switches;
    private final Gate gate;
    private final JCheckBox[] inputBoxes;
    private final Image image;
    private final Light light;

    private int rc;
    private int xc;
    private int yc;

    public GateView(Gate gate) {

        super(180, 80);

        this.gate = gate;

        light = new Light();
        light.setR(255);
        light.setG(0);
        light.setB(0);

        rc = 10;
        xc = 145 + rc;
        yc = 26 + rc;

        int inputSize = gate.getInputSize();

        switches = new Switch[inputSize];
        inputBoxes = new JCheckBox[inputSize];

        for (int i = 0; i < inputSize; i++) {
            switches[i] = new Switch();
            inputBoxes[i] = new JCheckBox();

            gate.connect(i, switches[i]);
        }

        light.connect(0, gate);

        int i = 0;
        int dy = 0;
        if (inputSize == 1) {
            dy = 12;
        }
        for (JCheckBox inputBox : inputBoxes) {
            add(inputBox, 14, 11 + 24 * i + dy, 75, 25);
            i++;
        }

        // Imagens das portas: https://en.wikipedia.org/wiki/Logic_gate
        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        addMouseListener(this);

        for (JCheckBox inputBox : inputBoxes) {
            inputBox.addActionListener(this);
        }

        update();
    }

    private void update() {
        for (int i = 0; i < gate.getInputSize(); i++) {
            if (inputBoxes[i].isSelected()) {
                switches[i].turnOn();
            } else {
                switches[i].turnOff();
            }
        }

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }

    @Override
    public void mouseClicked(MouseEvent event) {

        // Descobre em qual posição o clique ocorreu.
        int x = event.getX();
        int y = event.getY();

        int d2 = (x - xc) * (x - xc) + (y - yc) * (y - yc);

        // Se o clique foi dentro do circulo colorido...
        if (d2 <= rc * rc) {

            // ...então abrimos a janela seletora de cor...

            int r = light.getR();
            int g = light.getG();
            int b = light.getB();
            Color color = new Color(r, g, b);

            color = JColorChooser.showDialog(this, null, color);

            light.setR(color.getRed());
            light.setG(color.getGreen());
            light.setB(color.getBlue());

            // ...e chamamos repaint para atualizar a tela.
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de pressionar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de soltar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // entrar no painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseExited(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // sair do painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void paintComponent(Graphics gr) {

        // Não podemos esquecer desta linha, pois não somos os
        // únicos responsáveis por desenhar o painel, como era
        // o caso nos Desafios. Agora é preciso desenhar também
        // componentes internas, e isso é feito pela superclasse.
        super.paintComponent(gr);

        // Desenha a imagem, passando sua posição e seu tamanho.
        gr.drawImage(image, 30, 6, 120, 60, this);

        // Desenha um quadrado cheio.
        int r = light.getR();
        int g = light.getG();
        int b = light.getB();
        Color color = new Color(r, g, b);

        gr.setColor(color);
        gr.fillOval(xc - rc, yc - rc, 2 * rc, 2 * rc);

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}