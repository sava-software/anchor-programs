package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.lang.String;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.rpc.json.http.response.AccountInfo;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Pool(PublicKey _address,
                   Discriminator discriminator,
                   String name, byte[] _name,
                   Permissions permissions,
                   long inceptionTime,
                   PublicKey lpMint,
                   PublicKey oracleAuthority,
                   PublicKey stakedLpVault,
                   PublicKey rewardCustody,
                   PublicKey[] custodies,
                   TokenRatios[] ratios,
                   PublicKey[] markets,
                   long maxAumUsd,
                   long buffer,
                   long rawAumUsd,
                   long equityUsd,
                   StakeStats totalStaked,
                   long stakingFeeShareBps,
                   int bump,
                   int lpMintBump,
                   int stakedLpVaultBump,
                   int vpVolumeFactor,
                   int uniqueCustodyCount,
                   byte[] padding,
                   long[] stakingFeeBoostBps,
                   PublicKey compoundingMint,
                   PublicKey compoundingLpVault,
                   CompoundingStats compoundingStats,
                   int compoundingMintBump,
                   int compoundingLpVaultBump,
                   long minLpPriceUsd,
                   long maxLpPriceUsd,
                   long lpPrice,
                   long compoundingLpPrice,
                   long lastUpdatedTimestamp,
                   long[] padding2) implements Borsh {

  public static final int PADDING_LEN = 3;
  public static final int STAKING_FEE_BOOST_BPS_LEN = 6;
  public static final int PADDING_2_LEN = 1;
  public static final int NAME_OFFSET = 8;

  public static Pool createRecord(final PublicKey _address,
                                  final Discriminator discriminator,
                                  final String name,
                                  final Permissions permissions,
                                  final long inceptionTime,
                                  final PublicKey lpMint,
                                  final PublicKey oracleAuthority,
                                  final PublicKey stakedLpVault,
                                  final PublicKey rewardCustody,
                                  final PublicKey[] custodies,
                                  final TokenRatios[] ratios,
                                  final PublicKey[] markets,
                                  final long maxAumUsd,
                                  final long buffer,
                                  final long rawAumUsd,
                                  final long equityUsd,
                                  final StakeStats totalStaked,
                                  final long stakingFeeShareBps,
                                  final int bump,
                                  final int lpMintBump,
                                  final int stakedLpVaultBump,
                                  final int vpVolumeFactor,
                                  final int uniqueCustodyCount,
                                  final byte[] padding,
                                  final long[] stakingFeeBoostBps,
                                  final PublicKey compoundingMint,
                                  final PublicKey compoundingLpVault,
                                  final CompoundingStats compoundingStats,
                                  final int compoundingMintBump,
                                  final int compoundingLpVaultBump,
                                  final long minLpPriceUsd,
                                  final long maxLpPriceUsd,
                                  final long lpPrice,
                                  final long compoundingLpPrice,
                                  final long lastUpdatedTimestamp,
                                  final long[] padding2) {
    return new Pool(_address,
                    discriminator,
                    name, name.getBytes(UTF_8),
                    permissions,
                    inceptionTime,
                    lpMint,
                    oracleAuthority,
                    stakedLpVault,
                    rewardCustody,
                    custodies,
                    ratios,
                    markets,
                    maxAumUsd,
                    buffer,
                    rawAumUsd,
                    equityUsd,
                    totalStaked,
                    stakingFeeShareBps,
                    bump,
                    lpMintBump,
                    stakedLpVaultBump,
                    vpVolumeFactor,
                    uniqueCustodyCount,
                    padding,
                    stakingFeeBoostBps,
                    compoundingMint,
                    compoundingLpVault,
                    compoundingStats,
                    compoundingMintBump,
                    compoundingLpVaultBump,
                    minLpPriceUsd,
                    maxLpPriceUsd,
                    lpPrice,
                    compoundingLpPrice,
                    lastUpdatedTimestamp,
                    padding2);
  }

  public static Pool read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Pool read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Pool read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Pool> FACTORY = Pool::read;

  public static Pool read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var name = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var permissions = Permissions.read(_data, i);
    i += Borsh.len(permissions);
    final var inceptionTime = getInt64LE(_data, i);
    i += 8;
    final var lpMint = readPubKey(_data, i);
    i += 32;
    final var oracleAuthority = readPubKey(_data, i);
    i += 32;
    final var stakedLpVault = readPubKey(_data, i);
    i += 32;
    final var rewardCustody = readPubKey(_data, i);
    i += 32;
    final var custodies = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(custodies);
    final var ratios = Borsh.readVector(TokenRatios.class, TokenRatios::read, _data, i);
    i += Borsh.lenVector(ratios);
    final var markets = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(markets);
    final var maxAumUsd = getInt64LE(_data, i);
    i += 8;
    final var buffer = getInt64LE(_data, i);
    i += 8;
    final var rawAumUsd = getInt64LE(_data, i);
    i += 8;
    final var equityUsd = getInt64LE(_data, i);
    i += 8;
    final var totalStaked = StakeStats.read(_data, i);
    i += Borsh.len(totalStaked);
    final var stakingFeeShareBps = getInt64LE(_data, i);
    i += 8;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var lpMintBump = _data[i] & 0xFF;
    ++i;
    final var stakedLpVaultBump = _data[i] & 0xFF;
    ++i;
    final var vpVolumeFactor = _data[i] & 0xFF;
    ++i;
    final var uniqueCustodyCount = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[3];
    i += Borsh.readArray(padding, _data, i);
    final var stakingFeeBoostBps = new long[6];
    i += Borsh.readArray(stakingFeeBoostBps, _data, i);
    final var compoundingMint = readPubKey(_data, i);
    i += 32;
    final var compoundingLpVault = readPubKey(_data, i);
    i += 32;
    final var compoundingStats = CompoundingStats.read(_data, i);
    i += Borsh.len(compoundingStats);
    final var compoundingMintBump = _data[i] & 0xFF;
    ++i;
    final var compoundingLpVaultBump = _data[i] & 0xFF;
    ++i;
    final var minLpPriceUsd = getInt64LE(_data, i);
    i += 8;
    final var maxLpPriceUsd = getInt64LE(_data, i);
    i += 8;
    final var lpPrice = getInt64LE(_data, i);
    i += 8;
    final var compoundingLpPrice = getInt64LE(_data, i);
    i += 8;
    final var lastUpdatedTimestamp = getInt64LE(_data, i);
    i += 8;
    final var padding2 = new long[1];
    Borsh.readArray(padding2, _data, i);
    return new Pool(_address,
                    discriminator,
                    name, name.getBytes(UTF_8),
                    permissions,
                    inceptionTime,
                    lpMint,
                    oracleAuthority,
                    stakedLpVault,
                    rewardCustody,
                    custodies,
                    ratios,
                    markets,
                    maxAumUsd,
                    buffer,
                    rawAumUsd,
                    equityUsd,
                    totalStaked,
                    stakingFeeShareBps,
                    bump,
                    lpMintBump,
                    stakedLpVaultBump,
                    vpVolumeFactor,
                    uniqueCustodyCount,
                    padding,
                    stakingFeeBoostBps,
                    compoundingMint,
                    compoundingLpVault,
                    compoundingStats,
                    compoundingMintBump,
                    compoundingLpVaultBump,
                    minLpPriceUsd,
                    maxLpPriceUsd,
                    lpPrice,
                    compoundingLpPrice,
                    lastUpdatedTimestamp,
                    padding2);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.writeVector(_name, _data, i);
    i += Borsh.write(permissions, _data, i);
    putInt64LE(_data, i, inceptionTime);
    i += 8;
    lpMint.write(_data, i);
    i += 32;
    oracleAuthority.write(_data, i);
    i += 32;
    stakedLpVault.write(_data, i);
    i += 32;
    rewardCustody.write(_data, i);
    i += 32;
    i += Borsh.writeVector(custodies, _data, i);
    i += Borsh.writeVector(ratios, _data, i);
    i += Borsh.writeVector(markets, _data, i);
    putInt64LE(_data, i, maxAumUsd);
    i += 8;
    putInt64LE(_data, i, buffer);
    i += 8;
    putInt64LE(_data, i, rawAumUsd);
    i += 8;
    putInt64LE(_data, i, equityUsd);
    i += 8;
    i += Borsh.write(totalStaked, _data, i);
    putInt64LE(_data, i, stakingFeeShareBps);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    _data[i] = (byte) lpMintBump;
    ++i;
    _data[i] = (byte) stakedLpVaultBump;
    ++i;
    _data[i] = (byte) vpVolumeFactor;
    ++i;
    _data[i] = (byte) uniqueCustodyCount;
    ++i;
    i += Borsh.writeArray(padding, _data, i);
    i += Borsh.writeArray(stakingFeeBoostBps, _data, i);
    compoundingMint.write(_data, i);
    i += 32;
    compoundingLpVault.write(_data, i);
    i += 32;
    i += Borsh.write(compoundingStats, _data, i);
    _data[i] = (byte) compoundingMintBump;
    ++i;
    _data[i] = (byte) compoundingLpVaultBump;
    ++i;
    putInt64LE(_data, i, minLpPriceUsd);
    i += 8;
    putInt64LE(_data, i, maxLpPriceUsd);
    i += 8;
    putInt64LE(_data, i, lpPrice);
    i += 8;
    putInt64LE(_data, i, compoundingLpPrice);
    i += 8;
    putInt64LE(_data, i, lastUpdatedTimestamp);
    i += 8;
    i += Borsh.writeArray(padding2, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + Borsh.lenVector(_name)
         + Borsh.len(permissions)
         + 8
         + 32
         + 32
         + 32
         + 32
         + Borsh.lenVector(custodies)
         + Borsh.lenVector(ratios)
         + Borsh.lenVector(markets)
         + 8
         + 8
         + 8
         + 8
         + Borsh.len(totalStaked)
         + 8
         + 1
         + 1
         + 1
         + 1
         + 1
         + Borsh.lenArray(padding)
         + Borsh.lenArray(stakingFeeBoostBps)
         + 32
         + 32
         + Borsh.len(compoundingStats)
         + 1
         + 1
         + 8
         + 8
         + 8
         + 8
         + 8
         + Borsh.lenArray(padding2);
  }
}
