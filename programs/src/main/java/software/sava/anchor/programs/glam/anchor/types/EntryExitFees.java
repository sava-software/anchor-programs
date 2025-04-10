package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record EntryExitFees(int subscriptionFeeBps, int redemptionFeeBps) implements Borsh {

  public static final int BYTES = 4;

  public static EntryExitFees read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var subscriptionFeeBps = getInt16LE(_data, i);
    i += 2;
    final var redemptionFeeBps = getInt16LE(_data, i);
    return new EntryExitFees(subscriptionFeeBps, redemptionFeeBps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, subscriptionFeeBps);
    i += 2;
    putInt16LE(_data, i, redemptionFeeBps);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
