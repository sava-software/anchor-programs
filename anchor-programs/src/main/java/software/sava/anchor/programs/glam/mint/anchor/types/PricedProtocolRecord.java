package software.sava.anchor.programs.glam.mint.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record PricedProtocolRecord(PublicKey integrationProgram,
                                   int protocolBitflag,
                                   BigInteger baseAssetAmount) implements Borsh {

  public static final int BYTES = 50;

  public static PricedProtocolRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var integrationProgram = readPubKey(_data, i);
    i += 32;
    final var protocolBitflag = getInt16LE(_data, i);
    i += 2;
    final var baseAssetAmount = getInt128LE(_data, i);
    return new PricedProtocolRecord(integrationProgram, protocolBitflag, baseAssetAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    integrationProgram.write(_data, i);
    i += 32;
    putInt16LE(_data, i, protocolBitflag);
    i += 2;
    putInt128LE(_data, i, baseAssetAmount);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
