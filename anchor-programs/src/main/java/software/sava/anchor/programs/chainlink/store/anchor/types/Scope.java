package software.sava.anchor.programs.chainlink.store.anchor.types;

import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt32LE;

public sealed interface Scope extends RustEnum permits
  Scope.Version,
  Scope.Decimals,
  Scope.Description,
  Scope.RoundData,
  Scope.LatestRoundData,
  Scope.Aggregator {

  static Scope read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> Version.INSTANCE;
      case 1 -> Decimals.INSTANCE;
      case 2 -> Description.INSTANCE;
      case 3 -> RoundData.read(_data, i);
      case 4 -> LatestRoundData.INSTANCE;
      case 5 -> Aggregator.INSTANCE;
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [Scope]", ordinal
      ));
    };
  }

  record Version() implements EnumNone, Scope {

    public static final Version INSTANCE = new Version();

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record Decimals() implements EnumNone, Scope {

    public static final Decimals INSTANCE = new Decimals();

    @Override
    public int ordinal() {
      return 1;
    }
  }

  record Description() implements EnumNone, Scope {

    public static final Description INSTANCE = new Description();

    @Override
    public int ordinal() {
      return 2;
    }
  }

  record RoundData(int val) implements EnumInt32, Scope {

    public static RoundData read(final byte[] _data, int i) {
      return new RoundData(getInt32LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 3;
    }
  }

  record LatestRoundData() implements EnumNone, Scope {

    public static final LatestRoundData INSTANCE = new LatestRoundData();

    @Override
    public int ordinal() {
      return 4;
    }
  }

  record Aggregator() implements EnumNone, Scope {

    public static final Aggregator INSTANCE = new Aggregator();

    @Override
    public int ordinal() {
      return 5;
    }
  }
}
