package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SwapFeeInternalLogV3(String poolName, byte[] _poolName,
                                   PublicKey owner,
                                   long feeAmount,
                                   long[] padding) implements Borsh {

  public static final int PADDING_LEN = 4;
  public static SwapFeeInternalLogV3 createRecord(final String poolName,
                                                  final PublicKey owner,
                                                  final long feeAmount,
                                                  final long[] padding) {
    return new SwapFeeInternalLogV3(poolName, poolName.getBytes(UTF_8),
                                    owner,
                                    feeAmount,
                                    padding);
  }

  public static SwapFeeInternalLogV3 read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var poolName = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var owner = readPubKey(_data, i);
    i += 32;
    final var feeAmount = getInt64LE(_data, i);
    i += 8;
    final var padding = new long[4];
    Borsh.readArray(padding, _data, i);
    return new SwapFeeInternalLogV3(poolName, poolName.getBytes(UTF_8),
                                    owner,
                                    feeAmount,
                                    padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_poolName, _data, i);
    owner.write(_data, i);
    i += 32;
    putInt64LE(_data, i, feeAmount);
    i += 8;
    i += Borsh.writeArrayChecked(padding, 4, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_poolName) + 32 + 8 + Borsh.lenArray(padding);
  }
}
