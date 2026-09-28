package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "teleop", group = "Competition")
public class teleop extends LinearOpMode{

    //define hardware to controller mapping








    //Define teleop method

    @Override
    public void runOpMode(){

        //Define which motor spins where

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        //Motors on different sides spin different ways Reverse to fix this
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // update telemetry

        telemetry.addData("Status", "Started");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()){
            double forward = -gamepad1.left_stick_y;
            double backwards = gamepad1.left_stick_y;
            double strafe = gamepad1.right_stick_x;
            double turn = gamepad1.right_stick_x;

            //define motor speeds
            frontLeft.setPower (forward + strafe + turn);
            backLeft.setPower  (forward - strafe + turn);
            frontRight.setPower(forward - strafe - turn);
            backRight.setPower (forward + strafe - turn);


            telemetry.addData("Forward", forward);
            telemetry.addData("Strafe", strafe);
            telemetry.addData("Turn", turn);
            telemetry.update();


        }












    }
}
