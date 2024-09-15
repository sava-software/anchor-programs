package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CompositionFee(PublicKey from,
                             int binId,
                             long tokenXFeeAmount,
                             long tokenYFeeAmount,
                             long protocolTokenXFeeAmount,
                             long protocolTokenYFeeAmount) implements Borsh {

  public static final int BYTES = 66;

  public static CompositionFee read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var from = readPubKey(_data, i);
    i += 32;
    final var binId = getInt16LE(_data, i);
    i += 2;
    final var tokenXFeeAmount = getInt64LE(_data, i);
    i += 8;
    final var tokenYFeeAmount = getInt64LE(_data, i);
    i += 8;
    final var protocolTokenXFeeAmount = getInt64LE(_data, i);
    i += 8;
    final var protocolTokenYFeeAmount = getInt64LE(_data, i);
    return new CompositionFee(from,
                              binId,
                              tokenXFeeAmount,
                              tokenYFeeAmount,
                              protocolTokenXFeeAmount,
                              protocolTokenYFeeAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    from.write(_data, i);
    i += 32;
    putInt16LE(_data, i, binId);
    i += 2;
    putInt64LE(_data, i, tokenXFeeAmount);
    i += 8;
    putInt64LE(_data, i, tokenYFeeAmount);
    i += 8;
    putInt64LE(_data, i, protocolTokenXFeeAmount);
    i += 8;
    putInt64LE(_data, i, protocolTokenYFeeAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
