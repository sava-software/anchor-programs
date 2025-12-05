package software.sava.anchor.programs.kamino.vaults.anchor.types;

import software.sava.core.borsh.RustEnum;

public sealed interface UpdateReserveWhitelistMode extends RustEnum permits
  UpdateReserveWhitelistMode.Invest,
  UpdateReserveWhitelistMode.AddAllocation {

  static UpdateReserveWhitelistMode read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> Invest.read(_data, i);
      case 1 -> AddAllocation.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [UpdateReserveWhitelistMode]", ordinal
      ));
    };
  }

  record Invest(int val) implements EnumInt8, UpdateReserveWhitelistMode {

    public static Invest read(final byte[] _data, int i) {
      return new Invest(_data[i] & 0xFF);
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record AddAllocation(int val) implements EnumInt8, UpdateReserveWhitelistMode {

    public static AddAllocation read(final byte[] _data, int i) {
      return new AddAllocation(_data[i] & 0xFF);
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }
}
