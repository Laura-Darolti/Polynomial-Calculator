package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.*;


public class PolynomialCalculator extends JFrame implements ActionListener {
    private JTextField inputField1, inputField2;
    private JButton addButton, subtractButton, multiplyButton, integrateButton, differentiateButton;
    private JLabel resultLabel;

    public PolynomialCalculator() {
        super("Polynomial Calculator");

        inputField1 = new JTextField(20);
        inputField2 = new JTextField(20);
        addButton = new JButton("Add");
        addButton.addActionListener(this);
        subtractButton = new JButton("Subtract");
        subtractButton.addActionListener(this);
        multiplyButton = new JButton("Multiply");
        multiplyButton.addActionListener(this);
        integrateButton = new JButton("Integrate");
        integrateButton.addActionListener(this);
        differentiateButton = new JButton("Differentiate");
        differentiateButton.addActionListener(this);
        resultLabel = new JLabel("");

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("Enter first polynomial:"));
        panel.add(inputField1);
        panel.add(new JLabel("Enter second polynomial:"));
        panel.add(inputField2);
        panel.add(addButton);
        panel.add(subtractButton);
        panel.add(multiplyButton);
        panel.add(integrateButton);
        panel.add(differentiateButton);
        panel.add(resultLabel);

        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(300,400);
    }
    public Polynom stringToPolynomial(String input){
        String[] poly =  input.replace("^", "").split("((?=\\+)|(?=\\-)|x)");
        Polynom polynom = new Polynom();


        for (int i = 0; i < poly.length; i += 2) {
            var coefficient = Double.parseDouble(poly[i]);
            var exponent = Integer.parseInt(poly[i + 1]);
            polynom.getTreeMap().put(exponent, coefficient);
        }
        return  polynom;
    }
    public String addPolynomials(Polynom poly1, Polynom poly2) {

        for (Map.Entry<Integer, Double> entry2 : poly2.getTreeMap().entrySet()) {
            Integer exp2 = entry2.getKey();
            Double coeff2 = entry2.getValue();

            if(poly1.getTreeMap().containsKey(exp2)){
                var oldCoeff =  poly1.getTreeMap().get(exp2);
                var newCoeff =  coeff2+oldCoeff;
                if (newCoeff!=0){
                    poly1.getTreeMap().put(exp2, newCoeff);
                }
            }
            else {
                poly1.getTreeMap().put(exp2, coeff2);
            }
        }

    String string= new String();
        for (Map.Entry<Integer, Double> entry : poly1.getTreeMap().entrySet()) {
            Integer exp =entry.getKey();
            Double coef = entry.getValue();
            if(exp==0) {
                string=string+(coef.intValue());
            } else if(exp==1)
                string=string+(coef.intValue()+"x"+"+");
            else
                string=string+(coef.intValue()+"x^"+exp.intValue()+"+");
        }
        return string;

    }
    public String subtractPolynomials(Polynom poly1, Polynom poly2) {

        for (Map.Entry<Integer, Double> entry2 :  poly2.getTreeMap().entrySet()) {
            Integer exp2 = entry2.getKey();
            Double coeff2 = entry2.getValue();

            if(poly1.getTreeMap().containsKey(exp2)){
                var oldCoeff = poly1.getTreeMap().get(exp2);
                var newCoeff = oldCoeff-coeff2;
                if (newCoeff!=0){
                    poly1.getTreeMap().put(exp2, newCoeff);
                }
            }
            else {
                poly1.getTreeMap().put(exp2, -coeff2);
            }
        }
        String string=new String();
        for (Map.Entry<Integer, Double> entry : poly1.getTreeMap().entrySet()) {
            Integer exp =entry.getKey();
            Double coef = entry.getValue();
            if(exp==0) {
                string=string+(coef.intValue());
            } else if(exp==1)
                string=string+(coef.intValue()+"x"+"+");
            else
                string=string+(coef.intValue()+"x^"+exp.intValue()+"+");
        }
        return string;
    }

    public String multiplyPolynomials(String input1, String input2) {

        String[]  poly1 = input1.replace("^", "").split("((?=\\+)|(?=\\-)|x)");;
        String[]  poly2 = input2.replace("^", "").split("((?=\\+)|(?=\\-)|x)");

        Polynom polynom = new Polynom();


        for (int i = 0; i < poly1.length; i += 2) {
            var coeff1 = Double.parseDouble(poly1[i]);
            var exp1 = Integer.parseInt(poly1[i + 1]);

            for (int j = 0; j < poly2.length; j += 2){
                var coeff2 = Double.parseDouble(poly2[j]);
                var exp2 = Integer.parseInt(poly2[j + 1]);

                var newCoeff = coeff1 * coeff2;
                var newExp = exp1 + exp2;
                if(polynom.getTreeMap().containsKey(newExp)){

                    var oldCoeff = polynom.getTreeMap().get(newExp);
                    if (newCoeff+oldCoeff!=0)
                    polynom.getTreeMap().put(newExp, newCoeff+oldCoeff);
                }
                else {if (newCoeff!=0)
                    polynom.getTreeMap().put(newExp, newCoeff);
                }
            }
        }
        String string=new String();
        for (Map.Entry<Integer, Double> entry : polynom.getTreeMap().entrySet()) {
            Integer exp =entry.getKey();
            Double coef = entry.getValue();
            if(exp==0) {
                string=string+(coef.intValue());
            } else if(exp==1)
                string=string+(coef.intValue()+"x"+"+");
            else
                string=string+(coef.intValue()+"x^"+exp.intValue()+"+");
        }
        return string;

    }


    public String integratePolynomial(String input1) {

        String[] poly1 = input1.replace("^", "").split("((?=\\+)|(?=\\-)|x)");
        Polynom polynom = new Polynom();var fractions =new ArrayList<String>();

        for (int i = 0; i < poly1.length; i += 2) {
            Double coefficient = Double.parseDouble(poly1[i]);
            Integer exponent = Integer.parseInt(poly1[i + 1]);

            Monom monom = new Monom(exponent + 1, coefficient / (exponent + 1));
            polynom.addMonomial(monom);
        }
        String string=new String();
        DecimalFormat df = new DecimalFormat("#.##");
        for (Map.Entry<Integer, Double> entry : polynom.getTreeMap().entrySet()) {

            Integer exp =entry.getKey();
            Double coef = entry.getValue();
            string=string+(df.format(coef)+"x^"+exp+"+");
        }
        return string;
    }


    public String differentiatePolynomial(String input1) {

        String[] poly1 = input1.replace("^", "").split("((?=\\+)|(?=\\-)|x)");
        Polynom polynom = new Polynom();
        for (int i = 0; i < poly1.length; i += 2) {
            var coefficient = Double.parseDouble(poly1[i]);
            var exponent = Integer.parseInt(poly1[i + 1]);
            if (exponent != 0) {
                Monom monom = new Monom(exponent - 1, coefficient * exponent);
                polynom.addMonomial(monom);
            }
        }
        String string=new String();
        for (Map.Entry<Integer, Double> entry : polynom.getTreeMap().entrySet()) {
            Integer exp =entry.getKey();
            Double coef = entry.getValue();
            if(exp==0) {
                string=string+(coef.intValue());
            } else if(exp==1)
                string=string+(coef.intValue()+"x"+"+");
            else
                string=string+(coef.intValue()+"x^"+exp.intValue()+"+");
        }
        return string;
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String input1 = inputField1.getText();
            String input2 = inputField2.getText();
            String result = addPolynomials(stringToPolynomial(input1),stringToPolynomial(input2));
            resultLabel.setText("Result: " + result);
        } else if (e.getSource() == subtractButton) {
            String input1 = inputField1.getText();
            String input2 = inputField2.getText();
            String result1 = subtractPolynomials(stringToPolynomial(input1),stringToPolynomial(input2));
            resultLabel.setText("Result: " + result1);
        } else if (e.getSource() == multiplyButton) {
            String input1 = inputField1.getText();
            String input2 = inputField2.getText();
            String result2 = multiplyPolynomials(input1,input2);
            resultLabel.setText("Result: " + result2);
        } else if (e.getSource() == integrateButton) {
            String input1 = inputField1.getText();
            String result3 = integratePolynomial(input1);
            resultLabel.setText("Result: " + result3);
        } else if (e.getSource() == differentiateButton) {
            String input1 = inputField1.getText();
            String result4 = differentiatePolynomial(input1);
            resultLabel.setText("Result: " + result4);
        }
    }

    public static void main(String[] args) {
         new PolynomialCalculator();


    }
}