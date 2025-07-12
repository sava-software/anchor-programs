package software.sava.anchor.programs.jito.tip_router.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record WeightEntry(StMintEntry stMintEntry,
                          BigInteger weight,
                          long slotSet,
                          long slotUpdated,
                          byte[] reserved) implements Borsh {

  public static final int BYTES = 377;
  public static final int RESERVED_LEN = 128;

  public static WeightEntry read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var stMintEntry = StMintEntry.read(_data, i);
    i += Borsh.len(stMintEntry);
    final var weight = getInt128LE(_data, i);
    i += 16;
    final var slotSet = getInt64LE(_data, i);
    i += 8;
    final var slotUpdated = getInt64LE(_data, i);
    i += 8;
    final var reserved = new byte[128];
    Borsh.readArray(reserved, _data, i);
    return new WeightEntry(stMintEntry,
                           weight,
                           slotSet,
                           slotUpdated,
                           reserved);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(stMintEntry, _data, i);
    putInt128LE(_data, i, weight);
    i += 16;
    putInt64LE(_data, i, slotSet);
    i += 8;
    putInt64LE(_data, i, slotUpdated);
    i += 8;
    i += Borsh.writeArray(reserved, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
