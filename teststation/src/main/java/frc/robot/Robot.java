/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.             change here.    blah blah blah balh                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;





public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
  private CANSparkMax m_motor;
  private CANSparkMax m_motor2;


  Spark spark0 = new Spark(0);
  Spark spark1 = new Spark(1);
  Spark spark2 = new Spark(2);
  Spark spark3 = new Spark(3);
  XboxController controller1 = new XboxController(1);
  XboxController controller2 = new XboxController(2);
  Timer clock = new Timer();

 // buttons 1,2,3,4 on controller 1. Teleop Periodic
  boolean Fconstantmotor0 = false ;
  boolean Rconstantmotor0 = false;
  boolean Fconstantmotor1 = false;
  boolean Rconstantmotor1 = false;
  
 // buttons 1,2,3,4 controller 2. Teleop Perioidc
  boolean Fconstantmotor2 = false;
  boolean Rconstantmotor2 = false;
  boolean Fconstantmotor3 = false;
  boolean Rconstantmotor3 = false;
 
  // Button used for toggle switch on controller. Teleop Perioidic
  boolean manualMode1 = false;
  boolean manualMode2 = false ;

// These are all the trigger buttons on the controller. The backmost ones (7 & 8) make the motors go full speed, the inward two make them go half speed.
// "allmotorson" = turn all 4 sparks on at once. ( F being forward, R being reverse).
// allmotorshalf = turn all 4 sparks on at once at half speed.

  boolean allmotorsonF1 = false;
  boolean allmotorsonR1 = false;
  boolean allmotorshalfF1 = false;
  boolean allmotorshalfR1 = false;
  
  // This is the same thing but for controller 2
  boolean allmotorsonF2 = false;
  boolean allmotorsonR2 = false;
  boolean allmotorshalfF2 = false;
  boolean allmotorshalfR2 = false;

  double motorstarttime = 0;


  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    clock.start();

    m_motor = new CANSparkMax(10, MotorType.kBrushless);
   // m_motor.restoreFactoryDefaults();
    m_motor2 = new CANSparkMax(11, MotorType.kBrushless);
    
  }



  private NetworkTableEntry shuffeleboardbutton;
  

  @Override
  public void robotPeriodic() {
    // whatever goes here happens ALL the time the robot is on. regardless of mode.
  }

  

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    
  }



  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }

    m_motor.set(.5) ;



  }

  

  @Override
  public void teleopPeriodic() {

    SmartDashboard.putBoolean("manual mode 1:", manualMode1);

// This defines all the joystick values (the Y and X axes, which are labeled as "1"and "3" on the output, for some reason)
// The "if" statements put tolerance on the joystick. Basically "If the joystick is really close to 0, call it 0."
// Otherwise, we run into issues because someone will set down the joystick and it is just *barely* not 0, so the motor will still be running.

double Ljoystick1 = controller1.getRawAxis(1);
if (Math.abs(Ljoystick1) < .05) { Ljoystick1 = 0 ;}
double Rjoystick1 = controller1.getRawAxis(3);
if (Math.abs(Rjoystick1) < .05) { Rjoystick1 = 0 ;}
double Ljoystick2 = controller2.getRawAxis(1);
if (Math.abs(Ljoystick2) < .05) { Ljoystick2 = 0 ;}
double Rjoystick2 = controller2.getRawAxis(3);
if (Math.abs(Rjoystick2) < .05) { Rjoystick2 = 0 ;}

// These are all the trigger buttons on the controller. The backmost ones (7 & 8) make the motors go full speed, the inward two make them go half speed.
// "allmotorson" = turn all 4 sparks on at once. ( F being forward, R being reverse).
// allmotorshalf = turn all 4 sparks on at once at half speed.

allmotorsonF1 = controller1.getRawButton(7);
allmotorsonR1 = controller1.getRawButton(8);
allmotorshalfF1 = controller1.getRawButton(6);
allmotorshalfR1 = controller1.getRawButton(5);

// This is the same thing but for controller 2
allmotorsonF2 = controller2.getRawButton(7);
allmotorsonR2 = controller2.getRawButton(8);
allmotorshalfF2 = controller2.getRawButton(6);
allmotorshalfR2 = controller2.getRawButton(5);


    // This sets the toggle button: if it hasn't been pressed (false), and I press it, set the toggle to true. If I press it again and it's true, set it to false,
    // then this should just continually loop.

    if (controller1.getRawButtonPressed(9) == true) {
      if ( manualMode1 == false){
        manualMode1 = true ;
      } else { 
        manualMode1 = false ;
      }
    }

    if (controller2.getRawButtonPressed(9) == true) {
      if ( manualMode2 == false){
        manualMode2 = true ;
      } else { 
        manualMode2= false ;
      }
    }



    if (manualMode1 == true){
      spark0.set(Ljoystick1);
      spark1.set(Rjoystick1);
    }
    if (manualMode2 == true){
      spark0.set(Ljoystick2);
      spark1.set(Rjoystick2);
        }


// This means I'm taking the buttons "1,2,3,4" on our controllers, and assigning 4 & 2 together, and 1 & 3 together. 4 moves the motor attached to the
// left joystick forward, "Fconstantmotor", the F is for forward. and 2 moves it in reverse. 

Fconstantmotor0 = controller1.getRawButton(4);
Rconstantmotor0 = controller1.getRawButton(2);
Fconstantmotor1 = controller1.getRawButton(1);
Rconstantmotor1 = controller1.getRawButton(3);


// same thing but for the other controller

Fconstantmotor2 = controller2.getRawButton(4);
Rconstantmotor2 = controller2.getRawButton(2);
Fconstantmotor3 = controller2.getRawButton(1);
Rconstantmotor3 = controller2.getRawButton(3);
// same thing but for the other controller

// This defines 2 stop buttons, one on each controllr.
boolean stop1 = controller1.getRawButton(10);
boolean stop2 = controller2.getRawButton(10);

// This defines the "D-pad" on the controller. D-pad = the POV.
int dpad1 = controller1.getPOV();
int dpad2 = controller2.getPOV();


// Toggle button, if hit to give buttons priority, this gives sparks 0 and 1 on controller1 control. This "If statement" begins at line 172 and ends on line 206.
 // This is for controller 1
  // This is for spark 0.
if (manualMode1 == false) {
  if ( Fconstantmotor0 == true){
    spark0.set(0.6);
  }  else if (Rconstantmotor0 == true){
    spark0.set(-0.6);
  } 
  // CORRESPONDING MOTOR: DPAD FOR SMALL ADJUSTMENT VALUES UP AND DOWN
  if ( dpad1 == 0 ){
    double currentvalue = spark0.get();
    spark0.set(currentvalue + 0.01);
  } else if (dpad1 == 180){
    double currentvalue = spark0.get();
    spark0.set(currentvalue - 0.01);
  }



  // This is for spark 1
  if ( Fconstantmotor1 == true){
  spark1.set(0.6);
  }  else if (Rconstantmotor1 == true){
  spark1.set(-0.6);
  }


// CORRESPONDING MOTOR: DPAD FOR SMALL ADJUSTEMENT VALUES UP AND DOWN

  if ( dpad1 == 90 ){
  double currentvalue = spark1.get();
  spark1.set(currentvalue + 0.01);
  } else if (dpad1 == 270){
  double currentvalue = spark1.get();
  spark1.set(currentvalue - 0.01);
  }
}

// Toggle button, if hit to give buttons priority, this gives sparks 2 and 3 on controller1 control. This "else if statement" begins at line 212 and ends on 247.
 // This "else if" refers back to the "if" statement back on line 173.
  // This is for controller 2
  // This is for spark 2.
  if (manualMode2 == false ){
  if (Fconstantmotor2 == true){
    spark2.set(0.6);
  }  else if ( Rconstantmotor2 == true){
  spark2.set(-0.6);
  }

// CORRESPONDING MOTOR: DPAD FOR SMALL ADJUSTEMENT VALUES UP AND DOWN

  if ( dpad2 == 0 ){
      double currentvalue = spark2.get();
      spark2.set(currentvalue + 0.01);
  } else if (dpad2 == 180){
      double currentvalue = spark2.get();
      spark2.set(currentvalue - 0.01);
  }


  // This is spark 3.
  if ( Fconstantmotor3 == true){
    spark3.set(0.6);
  }  else if (Rconstantmotor3 == true){
      spark3.set(-0.6);
  }
// CORRESPONDING MOTOR: DPAD FOR SMALL ADJUSTEMENT VALUES UP AND DOWN

  if ( dpad2 == 90 ){
    double currentvalue = spark3.get();
    spark3.set(currentvalue + 0.01);
  } else if (dpad2 == 270){
    double currentvalue = spark3.get();
    spark3.set(currentvalue - 0.01);
  }
}


if ( stop1 == true || stop2 == true){
  spark0.set(0);
  spark1.set(0);
  spark2.set(0);
  spark3.set(0);
  m_motor.set(0);
  m_motor2.set(0);
}
// this is the corresponding code to the booleans setting each pair of buttons to one in a positive direction, and the other in a negative direction for
// both controllers.

if ( allmotorsonF1 == true || allmotorsonF2 == true){
  spark0.set(1);
  spark1.set(1);
  spark2.set(1);
  m_motor.set(1);
  m_motor2.set(-1);
  //spark3.set(1);
}
if ( allmotorsonR1 == true || allmotorsonR2 == true ){
  spark0.set(-1);
  spark1.set(-1);
  spark2.set(-1);
  m_motor.set(-1);
  m_motor2.set(1)
;  //spark3.set(-1);
}


if ( allmotorshalfF1 == true || allmotorshalfF2 == true){
  spark0.set(0.5);
  spark1.set(0.5);
  spark2.set(0.5);
  m_motor.set(0.5);
  m_motor2.set(-0.5);
  //spark3.set(0.5);
}
if ( allmotorshalfR1 == true || allmotorshalfR2 == true){
  spark0.set(-0.5);
  spark1.set(-0.5);
  spark2.set(-0.5);
  m_motor.set(-0.5);
  m_motor2.set(0.5);
  //spark3.set(-0.5);
}

indexspinner();

  }

  



  @Override
  public void testPeriodic() {
  }



  public void indexspinner() {

    boolean spinalittle1 = controller1.getRawButtonPressed(12);
    boolean spinalittle2 = controller1.getRawButtonPressed(11);
    boolean spinalittle3 = controller2.getRawButtonPressed(12);
    boolean spinalittle4 = controller2.getRawButtonPressed(11);
    

   if (spinalittle1 == true || spinalittle3 == true){
     spark3.set(0.7);
     motorstarttime = clock.get();
   }

    SmartDashboard.putNumber("motor start time:", motorstarttime);
    double timelag =  ( clock.get() - motorstarttime) ;
    SmartDashboard.putNumber("time lag:", timelag);
    if ( motorstarttime > 0 && ( clock.get() - motorstarttime) > 0.25){
     spark3.set(0);
      motorstarttime = 0 ;  
   }
   if (spinalittle2 == true || spinalittle4 == true){
     spark3.set(-1);
   }
  }
  public void sparkmaxtester() {






  }

}


