package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Swap(PublicKey lbPair,
                   PublicKey from,
                   int startBinId,
                   int endBinId,
                   long amountIn,
                   long amountOut,
                   boolean swapForY,
                   long fee,
                   long protocolFee,
                   BigInteger feeBps,
                   long hostFee) implements Borsh {

  public static final int BYTES = 129;

  public static Swap read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lbPair = readPubKey(_data, i);
    i += 32;
    final var from = readPubKey(_data, i);
    i += 32;
    final var startBinId = getInt32LE(_data, i);
    i += 4;
    final var endBinId = getInt32LE(_data, i);
    i += 4;
    final var amountIn = getInt64LE(_data, i);
    i += 8;
    final var amountOut = getInt64LE(_data, i);
    i += 8;
    final var swapForY = _data[i] == 1;
    ++i;
    final var fee = getInt64LE(_data, i);
    i += 8;
    final var protocolFee = getInt64LE(_data, i);
    i += 8;
    final var feeBps = getInt128LE(_data, i);
    i += 16;
    final var hostFee = getInt64LE(_data, i);
    return new Swap(lbPair,
                    from,
                    startBinId,
                    endBinId,
                    amountIn,
                    amountOut,
                    swapForY,
                    fee,
                    protocolFee,
                    feeBps,
                    hostFee);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    lbPair.write(_data, i);
    i += 32;
    from.write(_data, i);
    i += 32;
    putInt32LE(_data, i, startBinId);
    i += 4;
    putInt32LE(_data, i, endBinId);
    i += 4;
    putInt64LE(_data, i, amountIn);
    i += 8;
    putInt64LE(_data, i, amountOut);
    i += 8;
    _data[i] = (byte) (swapForY ? 1 : 0);
    ++i;
    putInt64LE(_data, i, fee);
    i += 8;
    putInt64LE(_data, i, protocolFee);
    i += 8;
    putInt128LE(_data, i, feeBps);
    i += 16;
    putInt64LE(_data, i, hostFee);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
