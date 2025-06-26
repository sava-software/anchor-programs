package software.sava.anchor.programs.pump.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SetParamsEvent(PublicKey feeRecipient,
                             long initialVirtualTokenReserves,
                             long initialVirtualSolReserves,
                             long initialRealTokenReserves,
                             long tokenTotalSupply,
                             long feeBasisPoints) implements Borsh {

  public static final int BYTES = 72;

  public static SetParamsEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var feeRecipient = readPubKey(_data, i);
    i += 32;
    final var initialVirtualTokenReserves = getInt64LE(_data, i);
    i += 8;
    final var initialVirtualSolReserves = getInt64LE(_data, i);
    i += 8;
    final var initialRealTokenReserves = getInt64LE(_data, i);
    i += 8;
    final var tokenTotalSupply = getInt64LE(_data, i);
    i += 8;
    final var feeBasisPoints = getInt64LE(_data, i);
    return new SetParamsEvent(feeRecipient,
                              initialVirtualTokenReserves,
                              initialVirtualSolReserves,
                              initialRealTokenReserves,
                              tokenTotalSupply,
                              feeBasisPoints);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    feeRecipient.write(_data, i);
    i += 32;
    putInt64LE(_data, i, initialVirtualTokenReserves);
    i += 8;
    putInt64LE(_data, i, initialVirtualSolReserves);
    i += 8;
    putInt64LE(_data, i, initialRealTokenReserves);
    i += 8;
    putInt64LE(_data, i, tokenTotalSupply);
    i += 8;
    putInt64LE(_data, i, feeBasisPoints);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
