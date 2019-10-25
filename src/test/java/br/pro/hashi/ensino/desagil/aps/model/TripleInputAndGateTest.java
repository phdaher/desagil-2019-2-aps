package br.pro.hashi.ensino.desagil.aps.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TripleInputAndGateTest {
    private TripleInputAndGate gate;

    private void build(boolean a, boolean b, boolean c) {
        gate = new TripleInputAndGate();
        gate.connect(0, new MockEmitter(a));
        gate.connect(1, new MockEmitter(b));
        gate.connect(2, new MockEmitter(c));
    }

    @Test
    public void whenReceivingFalseAndFalseAndFalseShouldReturnFalse() {
        build(false, false, false);
        Assertions.assertFalse(gate.read());
    }

    @Test
    public void whenReceivingFalseAndFalseAndTrueShouldReturnFalse() {
        build(false, false, true);
        Assertions.assertFalse(gate.read());
    }

    @Test
    public void whenReceivingFalseAndTrueAndFalseShouldReturnFalse() {
        build(false, true, false);
        Assertions.assertFalse(gate.read());
    }

    @Test
    public void whenReceivingTrueAndFalseAndFalseShouldReturnFalse() {
        build(true, false, false);
        Assertions.assertFalse(gate.read());
    }

    @Test
    public void whenReceivingTrueAndTrueAndFalseShouldReturnFalse() {
        build(true, true, false);
        Assertions.assertFalse(gate.read());
    }

    @Test
    public void whenReceivingTrueAndFalseAndTrueShouldReturnFalse() {
        build(true, false, true);
        Assertions.assertFalse(gate.read());
    }

    @Test
    public void whenReceivingFalseAndTrueAndTrueShouldReturnFalse() {
        build(false, true, true);
        Assertions.assertFalse(gate.read());
    }

    @Test
    public void whenReceivingTrueAndTrueAndTrueShouldReturnTrue() {
        build(true, true, true);
        Assertions.assertTrue(gate.read());
    }
}
