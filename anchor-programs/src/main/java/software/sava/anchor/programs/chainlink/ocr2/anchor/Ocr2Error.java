package software.sava.anchor.programs.chainlink.ocr2.anchor;

import software.sava.anchor.ProgramError;

public sealed interface Ocr2Error extends ProgramError permits
    Ocr2Error.Unauthorized,
    Ocr2Error.InvalidInput,
    Ocr2Error.TooManyOracles,
    Ocr2Error.StaleReport,
    Ocr2Error.DigestMismatch,
    Ocr2Error.WrongNumberOfSignatures,
    Ocr2Error.Overflow,
    Ocr2Error.MedianOutOfRange,
    Ocr2Error.DuplicateSigner,
    Ocr2Error.DuplicateTransmitter,
    Ocr2Error.PayeeAlreadySet,
    Ocr2Error.PayeeOracleMismatch,
    Ocr2Error.InvalidTokenAccount,
    Ocr2Error.UnauthorizedSigner,
    Ocr2Error.UnauthorizedTransmitter {

  static Ocr2Error getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> Unauthorized.INSTANCE;
      case 6001 -> InvalidInput.INSTANCE;
      case 6002 -> TooManyOracles.INSTANCE;
      case 6003 -> StaleReport.INSTANCE;
      case 6004 -> DigestMismatch.INSTANCE;
      case 6005 -> WrongNumberOfSignatures.INSTANCE;
      case 6006 -> Overflow.INSTANCE;
      case 6007 -> MedianOutOfRange.INSTANCE;
      case 6008 -> DuplicateSigner.INSTANCE;
      case 6009 -> DuplicateTransmitter.INSTANCE;
      case 6010 -> PayeeAlreadySet.INSTANCE;
      case 6011 -> PayeeOracleMismatch.INSTANCE;
      case 6012 -> InvalidTokenAccount.INSTANCE;
      case 6013 -> UnauthorizedSigner.INSTANCE;
      case 6014 -> UnauthorizedTransmitter.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Ocr2 error code: " + errorCode);
    };
  }

  record Unauthorized(int code, String msg) implements Ocr2Error {

    public static final Unauthorized INSTANCE = new Unauthorized(
        6000, "Unauthorized"
    );
  }

  record InvalidInput(int code, String msg) implements Ocr2Error {

    public static final InvalidInput INSTANCE = new InvalidInput(
        6001, "Invalid input"
    );
  }

  record TooManyOracles(int code, String msg) implements Ocr2Error {

    public static final TooManyOracles INSTANCE = new TooManyOracles(
        6002, "Too many oracles"
    );
  }

  record StaleReport(int code, String msg) implements Ocr2Error {

    public static final StaleReport INSTANCE = new StaleReport(
        6003, "Stale report"
    );
  }

  record DigestMismatch(int code, String msg) implements Ocr2Error {

    public static final DigestMismatch INSTANCE = new DigestMismatch(
        6004, "Digest mismatch"
    );
  }

  record WrongNumberOfSignatures(int code, String msg) implements Ocr2Error {

    public static final WrongNumberOfSignatures INSTANCE = new WrongNumberOfSignatures(
        6005, "Wrong number of signatures"
    );
  }

  record Overflow(int code, String msg) implements Ocr2Error {

    public static final Overflow INSTANCE = new Overflow(
        6006, "Overflow"
    );
  }

  record MedianOutOfRange(int code, String msg) implements Ocr2Error {

    public static final MedianOutOfRange INSTANCE = new MedianOutOfRange(
        6007, "Median out of range"
    );
  }

  record DuplicateSigner(int code, String msg) implements Ocr2Error {

    public static final DuplicateSigner INSTANCE = new DuplicateSigner(
        6008, "Duplicate signer"
    );
  }

  record DuplicateTransmitter(int code, String msg) implements Ocr2Error {

    public static final DuplicateTransmitter INSTANCE = new DuplicateTransmitter(
        6009, "Duplicate transmitter"
    );
  }

  record PayeeAlreadySet(int code, String msg) implements Ocr2Error {

    public static final PayeeAlreadySet INSTANCE = new PayeeAlreadySet(
        6010, "Payee already set"
    );
  }

  record PayeeOracleMismatch(int code, String msg) implements Ocr2Error {

    public static final PayeeOracleMismatch INSTANCE = new PayeeOracleMismatch(
        6011, "Payee and Oracle length mismatch"
    );
  }

  record InvalidTokenAccount(int code, String msg) implements Ocr2Error {

    public static final InvalidTokenAccount INSTANCE = new InvalidTokenAccount(
        6012, "Invalid Token Account"
    );
  }

  record UnauthorizedSigner(int code, String msg) implements Ocr2Error {

    public static final UnauthorizedSigner INSTANCE = new UnauthorizedSigner(
        6013, "Oracle signer key not found"
    );
  }

  record UnauthorizedTransmitter(int code, String msg) implements Ocr2Error {

    public static final UnauthorizedTransmitter INSTANCE = new UnauthorizedTransmitter(
        6014, "Oracle transmitter key not found"
    );
  }
}
