package software.sava.anchor.programs.pyth.receiver.anchor.types;

import software.sava.core.borsh.Borsh;

public record PostUpdateParams(MerklePriceUpdate merklePriceUpdate, int treasuryId) implements Borsh {

  public static PostUpdateParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var merklePriceUpdate = MerklePriceUpdate.read(_data, i);
    i += Borsh.len(merklePriceUpdate);
    final var treasuryId = _data[i] & 0xFF;
    return new PostUpdateParams(merklePriceUpdate, treasuryId);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(merklePriceUpdate, _data, i);
    _data[i] = (byte) treasuryId;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(merklePriceUpdate) + 1;
  }
}
