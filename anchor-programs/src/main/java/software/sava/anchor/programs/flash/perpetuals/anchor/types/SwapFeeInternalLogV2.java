package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SwapFeeInternalLogV2(String poolName, byte[] _poolName,
                                   PublicKey owner,
                                   long custodyInUid,
                                   long custodyOutUid,
                                   long swapAmount,
                                   long rewardCustodyAmount,
                                   long inOracleAccountTime,
                                   int inOracleAccountType,
                                   long inOracleAccountPrice,
                                   int inOracleAccountPriceExponent,
                                   long outOracleAccountTime,
                                   int outOracleAccountType,
                                   long outOracleAccountPrice,
                                   int outOracleAccountPriceExponent) implements Borsh {

  public static SwapFeeInternalLogV2 createRecord(final String poolName,
                                                  final PublicKey owner,
                                                  final long custodyInUid,
                                                  final long custodyOutUid,
                                                  final long swapAmount,
                                                  final long rewardCustodyAmount,
                                                  final long inOracleAccountTime,
                                                  final int inOracleAccountType,
                                                  final long inOracleAccountPrice,
                                                  final int inOracleAccountPriceExponent,
                                                  final long outOracleAccountTime,
                                                  final int outOracleAccountType,
                                                  final long outOracleAccountPrice,
                                                  final int outOracleAccountPriceExponent) {
    return new SwapFeeInternalLogV2(poolName, poolName.getBytes(UTF_8),
                                    owner,
                                    custodyInUid,
                                    custodyOutUid,
                                    swapAmount,
                                    rewardCustodyAmount,
                                    inOracleAccountTime,
                                    inOracleAccountType,
                                    inOracleAccountPrice,
                                    inOracleAccountPriceExponent,
                                    outOracleAccountTime,
                                    outOracleAccountType,
                                    outOracleAccountPrice,
                                    outOracleAccountPriceExponent);
  }

  public static SwapFeeInternalLogV2 read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var poolName = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var owner = readPubKey(_data, i);
    i += 32;
    final var custodyInUid = getInt64LE(_data, i);
    i += 8;
    final var custodyOutUid = getInt64LE(_data, i);
    i += 8;
    final var swapAmount = getInt64LE(_data, i);
    i += 8;
    final var rewardCustodyAmount = getInt64LE(_data, i);
    i += 8;
    final var inOracleAccountTime = getInt64LE(_data, i);
    i += 8;
    final var inOracleAccountType = _data[i] & 0xFF;
    ++i;
    final var inOracleAccountPrice = getInt64LE(_data, i);
    i += 8;
    final var inOracleAccountPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var outOracleAccountTime = getInt64LE(_data, i);
    i += 8;
    final var outOracleAccountType = _data[i] & 0xFF;
    ++i;
    final var outOracleAccountPrice = getInt64LE(_data, i);
    i += 8;
    final var outOracleAccountPriceExponent = getInt32LE(_data, i);
    return new SwapFeeInternalLogV2(poolName, poolName.getBytes(UTF_8),
                                    owner,
                                    custodyInUid,
                                    custodyOutUid,
                                    swapAmount,
                                    rewardCustodyAmount,
                                    inOracleAccountTime,
                                    inOracleAccountType,
                                    inOracleAccountPrice,
                                    inOracleAccountPriceExponent,
                                    outOracleAccountTime,
                                    outOracleAccountType,
                                    outOracleAccountPrice,
                                    outOracleAccountPriceExponent);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_poolName, _data, i);
    owner.write(_data, i);
    i += 32;
    putInt64LE(_data, i, custodyInUid);
    i += 8;
    putInt64LE(_data, i, custodyOutUid);
    i += 8;
    putInt64LE(_data, i, swapAmount);
    i += 8;
    putInt64LE(_data, i, rewardCustodyAmount);
    i += 8;
    putInt64LE(_data, i, inOracleAccountTime);
    i += 8;
    _data[i] = (byte) inOracleAccountType;
    ++i;
    putInt64LE(_data, i, inOracleAccountPrice);
    i += 8;
    putInt32LE(_data, i, inOracleAccountPriceExponent);
    i += 4;
    putInt64LE(_data, i, outOracleAccountTime);
    i += 8;
    _data[i] = (byte) outOracleAccountType;
    ++i;
    putInt64LE(_data, i, outOracleAccountPrice);
    i += 8;
    putInt32LE(_data, i, outOracleAccountPriceExponent);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_poolName)
         + 32
         + 8
         + 8
         + 8
         + 8
         + 8
         + 1
         + 8
         + 4
         + 8
         + 1
         + 8
         + 4;
  }
}
