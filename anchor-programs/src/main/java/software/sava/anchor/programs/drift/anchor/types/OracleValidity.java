package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.RustEnum;

public sealed interface OracleValidity extends RustEnum permits
  OracleValidity.NonPositive,
  OracleValidity.TooVolatile,
  OracleValidity.TooUncertain,
  OracleValidity.StaleForMargin,
  OracleValidity.InsufficientDataPoints,
  OracleValidity.StaleForAMM,
  OracleValidity.Valid {

  static OracleValidity read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> NonPositive.INSTANCE;
      case 1 -> TooVolatile.INSTANCE;
      case 2 -> TooUncertain.INSTANCE;
      case 3 -> StaleForMargin.INSTANCE;
      case 4 -> InsufficientDataPoints.INSTANCE;
      case 5 -> StaleForAMM.read(_data, i);
      case 6 -> Valid.INSTANCE;
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [OracleValidity]", ordinal
      ));
    };
  }

  record NonPositive() implements EnumNone, OracleValidity {

    public static final NonPositive INSTANCE = new NonPositive();

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record TooVolatile() implements EnumNone, OracleValidity {

    public static final TooVolatile INSTANCE = new TooVolatile();

    @Override
    public int ordinal() {
      return 1;
    }
  }

  record TooUncertain() implements EnumNone, OracleValidity {

    public static final TooUncertain INSTANCE = new TooUncertain();

    @Override
    public int ordinal() {
      return 2;
    }
  }

  record StaleForMargin() implements EnumNone, OracleValidity {

    public static final StaleForMargin INSTANCE = new StaleForMargin();

    @Override
    public int ordinal() {
      return 3;
    }
  }

  record InsufficientDataPoints() implements EnumNone, OracleValidity {

    public static final InsufficientDataPoints INSTANCE = new InsufficientDataPoints();

    @Override
    public int ordinal() {
      return 4;
    }
  }

  record StaleForAMM(boolean immediate, boolean lowRisk) implements OracleValidity {

    public static final int BYTES = 2;

    public static StaleForAMM read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var immediate = _data[i] == 1;
      ++i;
      final var lowRisk = _data[i] == 1;
      return new StaleForAMM(immediate, lowRisk);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      _data[i] = (byte) (immediate ? 1 : 0);
      ++i;
      _data[i] = (byte) (lowRisk ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }

    @Override
    public int ordinal() {
      return 5;
    }
  }

  record Valid() implements EnumNone, OracleValidity {

    public static final Valid INSTANCE = new Valid();

    @Override
    public int ordinal() {
      return 6;
    }
  }
}
