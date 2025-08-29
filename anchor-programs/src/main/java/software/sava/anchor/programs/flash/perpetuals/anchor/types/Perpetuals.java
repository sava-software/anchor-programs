package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Perpetuals(PublicKey _address,
                         Discriminator discriminator,
                         Permissions permissions,
                         PublicKey[] pools,
                         PublicKey[] collections,
                         VoltageMultiplier voltageMultiplier,
                         long[] tradingDiscount,
                         long[] referralRebate,
                         long defaultRebate,
                         long inceptionTime,
                         int transferAuthorityBump,
                         int perpetualsBump,
                         int tradeLimit,
                         int triggerOrderLimit,
                         int rebateLimitUsd) implements Borsh {

  public static final int TRADING_DISCOUNT_LEN = 6;
  public static final int REFERRAL_REBATE_LEN = 6;
  public static final int PERMISSIONS_OFFSET = 8;
  public static final int POOLS_OFFSET = 21;

  public static Filter createPermissionsFilter(final Permissions permissions) {
    return Filter.createMemCompFilter(PERMISSIONS_OFFSET, permissions.write());
  }

  public static Perpetuals read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Perpetuals read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Perpetuals read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Perpetuals> FACTORY = Perpetuals::read;

  public static Perpetuals read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var permissions = Permissions.read(_data, i);
    i += Borsh.len(permissions);
    final var pools = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(pools);
    final var collections = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(collections);
    final var voltageMultiplier = VoltageMultiplier.read(_data, i);
    i += Borsh.len(voltageMultiplier);
    final var tradingDiscount = new long[6];
    i += Borsh.readArray(tradingDiscount, _data, i);
    final var referralRebate = new long[6];
    i += Borsh.readArray(referralRebate, _data, i);
    final var defaultRebate = getInt64LE(_data, i);
    i += 8;
    final var inceptionTime = getInt64LE(_data, i);
    i += 8;
    final var transferAuthorityBump = _data[i] & 0xFF;
    ++i;
    final var perpetualsBump = _data[i] & 0xFF;
    ++i;
    final var tradeLimit = _data[i] & 0xFF;
    ++i;
    final var triggerOrderLimit = _data[i] & 0xFF;
    ++i;
    final var rebateLimitUsd = getInt32LE(_data, i);
    return new Perpetuals(_address,
                          discriminator,
                          permissions,
                          pools,
                          collections,
                          voltageMultiplier,
                          tradingDiscount,
                          referralRebate,
                          defaultRebate,
                          inceptionTime,
                          transferAuthorityBump,
                          perpetualsBump,
                          tradeLimit,
                          triggerOrderLimit,
                          rebateLimitUsd);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.write(permissions, _data, i);
    i += Borsh.writeVector(pools, _data, i);
    i += Borsh.writeVector(collections, _data, i);
    i += Borsh.write(voltageMultiplier, _data, i);
    i += Borsh.writeArray(tradingDiscount, _data, i);
    i += Borsh.writeArray(referralRebate, _data, i);
    putInt64LE(_data, i, defaultRebate);
    i += 8;
    putInt64LE(_data, i, inceptionTime);
    i += 8;
    _data[i] = (byte) transferAuthorityBump;
    ++i;
    _data[i] = (byte) perpetualsBump;
    ++i;
    _data[i] = (byte) tradeLimit;
    ++i;
    _data[i] = (byte) triggerOrderLimit;
    ++i;
    putInt32LE(_data, i, rebateLimitUsd);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + Borsh.len(permissions)
         + Borsh.lenVector(pools)
         + Borsh.lenVector(collections)
         + Borsh.len(voltageMultiplier)
         + Borsh.lenArray(tradingDiscount)
         + Borsh.lenArray(referralRebate)
         + 8
         + 8
         + 1
         + 1
         + 1
         + 1
         + 4;
  }
}
