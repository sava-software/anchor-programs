package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.RustEnum;

public sealed interface FillMode extends RustEnum permits
  FillMode.Fill,
  FillMode.PlaceAndMake,
  FillMode.PlaceAndTake,
  FillMode.Liquidation,
  FillMode.RFQ {

  static FillMode read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> Fill.INSTANCE;
      case 1 -> PlaceAndMake.INSTANCE;
      case 2 -> PlaceAndTake.read(_data, i);
      case 3 -> Liquidation.INSTANCE;
      case 4 -> RFQ.INSTANCE;
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [FillMode]", ordinal
      ));
    };
  }

  record Fill() implements EnumNone, FillMode {

    public static final Fill INSTANCE = new Fill();

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record PlaceAndMake() implements EnumNone, FillMode {

    public static final PlaceAndMake INSTANCE = new PlaceAndMake();

    @Override
    public int ordinal() {
      return 1;
    }
  }

  record PlaceAndTake(boolean val) implements EnumBool, FillMode {

    public static final PlaceAndTake TRUE = new PlaceAndTake(true);
    public static final PlaceAndTake FALSE = new PlaceAndTake(false);

    public static PlaceAndTake read(final byte[] _data, int i) {
      return _data[i] == 1 ? PlaceAndTake.TRUE : PlaceAndTake.FALSE;
    }

    @Override
    public int ordinal() {
      return 2;
    }
  }

  record Liquidation() implements EnumNone, FillMode {

    public static final Liquidation INSTANCE = new Liquidation();

    @Override
    public int ordinal() {
      return 3;
    }
  }

  record RFQ() implements EnumNone, FillMode {

    public static final RFQ INSTANCE = new RFQ();

    @Override
    public int ordinal() {
      return 4;
    }
  }
}
