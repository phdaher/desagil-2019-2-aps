package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// A classe JPanel representa uma das componentes mais
// simples da Swing. A função dela é simplesmente ser
// um contêiner para colocar outras componentes dentro.
// A razão de implementar ActionListener está mais abaixo.
public class GateView extends JPanel implements ActionListener {

    // A ideia é que essa componente gráfica represente
    // um gate específico. Esse gate que
    // está sendo representado é guardado como atributo.
    private final Gate gate;

    // A classe JCheckBox representa um checkbox.
    private final JCheckBox input01;
    private final JCheckBox input02;
    private final JCheckBox output;
    private final Switch switch01;
    private final Switch switch02;

    public GateView(Gate gate) {
        this.gate = gate;

        // Nada de especial na construção dos inputs.
        input01 = new JCheckBox();
        input02 = new JCheckBox();
        output = new JCheckBox();
        switch01 = new Switch();
        switch02 = new Switch();

        // A classe JLabel representa um rótulo, ou seja,
        // um texto não-editável que queremos colocar na
        // interface para identificar alguma coisa. Não
        // precisa ser atributo, pois não precisamos mais
        // mexer nesses objetos depois de criar e adicionar.
        JLabel inputLabel = new JLabel("Entrada:");
        JLabel outputLabel = new JLabel("Saída:");

        // Um JPanel tem um layout, ou seja, um padrão para
        // organizar as componentes dentro dele. A linha abaixo
        // estabelece um dos padrões mais simples: simplesmente
        // colocar uma componente debaixo da outra, alinhadas.
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Colocamos todas componentes aqui no contêiner.
        add(inputLabel);
        add(input01);
        if (gate.getInputSize() > 1) {
            add(input02);
        }
        add(outputLabel);
        add(output);

        // Um checkbox tem uma lista de observadores que
        // reagem quando o usuário mudar a seleção.
        // Usamos o método addActionListener para adicionar esta
        // instância de GateView, ou seja "this", nessa
        // lista. Só que addActionListener espera receber um objeto
        // do tipo ActionListener como parâmetro. É por isso que
        // adicionamos o "implements ActionListener" lá em cima.
        input01.addActionListener(this);
        input02.addActionListener(this);

        // O último checkbox não pode ser editável, pois é
        // só para exibição. Logo, configuramos como desabilitado.
        output.setEnabled(false);

        // Update é o método que definimos abaixo para atualizar o
        // último checkbox de acordo com os valores dos primeiros.
        // Precisamos chamar esse método no final da construção
        // para garantir que a interface não nasce inconsistente.
        update();
    }

    private void update() {
        if (input01.isSelected()) {
            switch01.turnOn();
        } else {
            switch01.turnOff();
        }

        if (input02.isSelected()) {
            switch02.turnOn();
        } else {
            switch02.turnOff();
        }

        gate.connect(0, switch01);
        if (gate.getInputSize() > 1) {
            gate.connect(1, switch02);
        }
        output.setSelected(gate.read());
    }

    // O que esta componente deve fazer quando o usuário mudar a
    // seleção de um input? Apenas chamar o update, é claro!
    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }
}
