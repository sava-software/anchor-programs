package software.sava.anchor.programs.pyth.receiver.anchor.types;

import software.sava.core.borsh.Borsh;

public record PostTwapUpdateParams(MerklePriceUpdate startMerklePriceUpdate,
                                   MerklePriceUpdate endMerklePriceUpdate,
                                   int treasuryId) implements Borsh {

  public static PostTwapUpdateParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var startMerklePriceUpdate = MerklePriceUpdate.read(_data, i);
    i += Borsh.len(startMerklePriceUpdate);
    final var endMerklePriceUpdate = MerklePriceUpdate.read(_data, i);
    i += Borsh.len(endMerklePriceUpdate);
    final var treasuryId = _data[i] & 0xFF;
    return new PostTwapUpdateParams(startMerklePriceUpdate, endMerklePriceUpdate, treasuryId);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(startMerklePriceUpdate, _data, i);
    i += Borsh.write(endMerklePriceUpdate, _data, i);
    _data[i] = (byte) treasuryId;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(startMerklePriceUpdate) + Borsh.len(endMerklePriceUpdate) + 1;
  }
}
