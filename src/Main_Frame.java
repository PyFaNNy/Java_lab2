import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import static java.lang.Math.*;


public class Main_Frame extends JFrame {
    private static final int width = 1000;
    private static final int height = 400;

    private JTextField result_field;
    private JTextField x_field;
    private JTextField y_field;
    private JTextField z_field;

    private JLabel image;

    private ButtonGroup radioButtons = new ButtonGroup();
    private Box formula_type = Box.createHorizontalBox();
    private ButtonGroup radioMemoryButtons = new ButtonGroup();
    private Box hBoxMemoryType = Box.createHorizontalBox();

    private JTextField memoryTextField;

    int formula_number = 1;

    public Double calculate1(Double x, Double y, Double z) {

        // Блок ОДЗ
        if (y <= 0)	{
            JOptionPane.showMessageDialog(Main_Frame.this,
                    "y должен быть положительным", "" +
                            "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }
        if ( x*x + sin(z) + pow(E,cos(z)) < 0)	{
            JOptionPane.showMessageDialog(Main_Frame.this,
                    "выражение под корнем должно быть положительным", "" +
                            "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }
        //

        return sin(log(y) + sin(PI*y*y))*pow((x*x + sin(z) + pow(E,cos(z))),1/4) ;
    }
    public Double calculate2(Double x, Double y, Double z) {

        // Блок ОДЗ
        if (y == -1)	{
            JOptionPane.showMessageDialog(Main_Frame.this,
                    "y не должен ровняться -1", "" +
                            "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }
        if (x <= 0)	{
            JOptionPane.showMessageDialog(Main_Frame.this,
                    "x должен быть положительным", "" +
                            "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }
        if (pow(E,cos(x)) + sin(PI*z)*sin(PI*z) < 0)	{
            JOptionPane.showMessageDialog(Main_Frame.this,
                    "выражение под корнем должно быть положительным", "" +
                            "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }
        //

        return pow(cos(pow(E ,x)) + log((1+y)*(1+y)) + sqrt(pow(E,cos(x)) + sin(PI*z)*sin(PI*z)) +sqrt(1/x) + cos(y*y),sin(z));
    }

    private void addRadioButton(String name, final int formula_number)           // радиокнопки для формул
    {
        JRadioButton button = new JRadioButton(name);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent)
            {
                Main_Frame.this.formula_number = formula_number;
                if (formula_number == 1)	image.setIcon(new ImageIcon(Main_Frame.class.getResource("formula_1.jpg")));
                if (formula_number == 2) image.setIcon(new ImageIcon(Main_Frame.class.getResource("formula_2.jpg")));
            }
        });
        radioButtons.add(button);
        formula_type.add(button);
    }

    public Main_Frame() {

        super("Вычисление формулы");
        setSize(width,height);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width-width)/2,
                (kit.getScreenSize().height-height)/2);



        Box picture = Box.createHorizontalBox();                    // область с картинкой
        picture.add(Box.createVerticalGlue());
        picture.add(Box.createHorizontalGlue());
        image = new JLabel(new ImageIcon(Main_Frame.class.getResource("formula_1.jpg")));
        picture.add(image);
        picture.add(Box.createHorizontalGlue());
        picture.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        formula_type.add(Box.createHorizontalGlue());              // область с выбором формул
        addRadioButton("Формула 1", 1);
        addRadioButton("Формула 2", 2);
        radioButtons.setSelected(radioButtons.getElements().nextElement().getModel(), true);
        formula_type.add(Box.createHorizontalGlue());
        formula_type.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

        Box data=Box.createHorizontalBox();                 // область с полями ввода значений
        data.add(Box.createHorizontalGlue());
        x_field = new JTextField("0",10);
        x_field.setMaximumSize(x_field.getPreferredSize());
        y_field = new JTextField("0",10);
        y_field.setMaximumSize(x_field.getPreferredSize());
        z_field = new JTextField("0",10);
        z_field.setMaximumSize(x_field.getPreferredSize());
        z_field.setMaximumSize(x_field.getPreferredSize());
        JLabel x_label=new JLabel("X:");
        JLabel y_label=new JLabel("Y:");
        JLabel z_label=new JLabel("Z:");
        data.add(Box.createHorizontalGlue());
        data.add(x_label);
        data.add(Box.createHorizontalStrut(10));
        data.add(x_field);
        data.add(Box.createHorizontalStrut(100));
        data.add(y_label);
        data.add(Box.createHorizontalStrut(10));
        data.add(y_field);
        data.add(Box.createHorizontalStrut(100));
        data.add(z_label);
        data.add(Box.createHorizontalStrut(10));
        data.add(z_field);
        data.add(Box.createHorizontalGlue());
        data.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        Box result_area = Box.createHorizontalBox();          // область для результата
        result_area.add(Box.createHorizontalGlue());
        JLabel resultlabel=new JLabel("Результат:");
        result_field = new JTextField("0",15);
        result_field.setMaximumSize(result_field.getPreferredSize());
        result_area.add(resultlabel);
        result_area.add(Box.createHorizontalStrut(10));
        result_area.add(result_field);
        result_area.add(Box.createHorizontalGlue());
        result_area.setBorder(BorderFactory.createLineBorder(Color.PINK));

        Box actions=Box.createHorizontalBox();                        // область для действий
        JButton calc_button=new JButton("Вычислить");
        calc_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                //при работе с числами с плавающей точки могут возникнуть ерроры которые мы обрабатываем
                try {
                    //X
                    Double x = Double.parseDouble(x_field.getText());
                    //Y
                    Double y = Double.parseDouble(y_field.getText());
                    //Z
                    Double z = Double.parseDouble(z_field.getText());
                    // Результат
                    Double result;

                    //Вычислить результат
                    if (formula_number==1)
                        result = calculate1(x, y, z);
                    else
                        result = calculate2(x, y, z);
                    //Установить текст надписи равным результату
                    result_field.setText(result.toString());
                } catch (NumberFormatException ex) {
                    //В случае исключительной ситуации показать сообщение
                    JOptionPane.showMessageDialog(Main_Frame.this, "Ошибка в" +
                                    "формате записи числа с плавающей точкой", "Ошибочный формат числа",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        JButton clean_button=new JButton("Очистить");
        clean_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                x_field.setText("0");
                y_field.setText("0");
                z_field.setText("0");
                result_field.setText("0");
            }
        });
        actions.add(Box.createHorizontalGlue());
        actions.add(calc_button);
        actions.add(Box.createHorizontalStrut(10));
        actions.add(clean_button);
        actions.add(Box.createHorizontalGlue());



        Box contentBox = Box.createVerticalBox();
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(picture);
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(formula_type);
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(data);
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(result_area);
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(actions);

        getContentPane().add(contentBox, BorderLayout.CENTER);

    }
}