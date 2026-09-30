package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "teleop", group = "Competition")
public class teleop extends LinearOpMode {

    @Override
    public void runOpMode() {

        // define motors
        DcMotor frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backRight  = hardwareMap.get(DcMotor.class, "backRight");
        DcMotor backLeft   = hardwareMap.get(DcMotor.class, "backLeft");
        DcMotor flyWheel   = hardwareMap.get(DcMotor.class, "flyWheel");

        boolean isShooterGoing = false;
        boolean lastBumper = false;

        // this fixes the bug where motors on different sides would spin the wrong way
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // This allows the motors to stop quickly
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flyWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // Drive bindings
            double forward = -gamepad1.left_stick_y;   // stick up is negative, so we flip up
            double strafe  =  gamepad1.left_stick_x;
            double turn    =  gamepad1.right_stick_x;

            //borrowed from examples
            double scale = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(turn), 1.0);

            frontLeft.setPower((forward + strafe + turn) / scale);
            backLeft.setPower((forward - strafe + turn) / scale);
            frontRight.setPower((forward - strafe - turn) / scale);
            backRight.setPower((forward + strafe - turn) / scale);

            // Shooter toggle
            boolean bumper = gamepad1.right_bumper;
            if (bumper && !lastBumper) {
                isShooterGoing = !isShooterGoing;
            }
            lastBumper = bumper;

            flyWheel.setPower(isShooterGoing ? 1.0 : 0.0);

            // Telemetry (one update per loop, after all the addData lines)
            telemetry.addData("Forward", forward);
            telemetry.addData("Strafe", strafe);
            telemetry.addData("Turn", turn);
            telemetry.addData("Shooter", isShooterGoing ? "ON" : "OFF");
            telemetry.update();
        }
    }
}