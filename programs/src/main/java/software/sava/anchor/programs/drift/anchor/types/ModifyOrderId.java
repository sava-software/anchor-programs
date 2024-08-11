package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt32LE;

public sealed interface ModifyOrderId extends RustEnum permits
  ModifyOrderId.UserOrderId,
  ModifyOrderId.OrderId {

  static ModifyOrderId read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    int i = offset + 1;
    return switch (ordinal) {
      case 0 -> new UserOrderId(_data[i] & 0xFF);
      case 1 -> new OrderId(getInt32LE(_data, i));
      default -> throw new IllegalStateException(java.lang.String.format("Unexpected ordinal [%d] for enum [ModifyOrderId].", ordinal));
    };
  }

  record UserOrderId(int val) implements EnumInt8, ModifyOrderId {

    public static UserOrderId read(final byte[] _data, int i) {
      return new UserOrderId(_data[i] & 0xFF);
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record OrderId(int val) implements EnumInt32, ModifyOrderId {

    public static OrderId read(final byte[] _data, int i) {
      return new OrderId(getInt32LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }
}
