package software.sava.anchor.programs.chainlink.ocr2.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Config(PublicKey owner,
                     PublicKey proposedOwner,
                     PublicKey tokenMint,
                     PublicKey tokenVault,
                     PublicKey requesterAccessController,
                     PublicKey billingAccessController,
                     BigInteger minAnswer,
                     BigInteger maxAnswer,
                     int f,
                     int round,
                     int padding0,
                     int epoch,
                     int latestAggregatorRoundId,
                     PublicKey latestTransmitter,
                     int configCount,
                     byte[] latestConfigDigest,
                     long latestConfigBlockNumber,
                     Billing billing) implements Borsh {

  public static final int BYTES = 320;
  public static final int LATEST_CONFIG_DIGEST_LEN = 32;

  public static Config read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var proposedOwner = readPubKey(_data, i);
    i += 32;
    final var tokenMint = readPubKey(_data, i);
    i += 32;
    final var tokenVault = readPubKey(_data, i);
    i += 32;
    final var requesterAccessController = readPubKey(_data, i);
    i += 32;
    final var billingAccessController = readPubKey(_data, i);
    i += 32;
    final var minAnswer = getInt128LE(_data, i);
    i += 16;
    final var maxAnswer = getInt128LE(_data, i);
    i += 16;
    final var f = _data[i] & 0xFF;
    ++i;
    final var round = _data[i] & 0xFF;
    ++i;
    final var padding0 = getInt16LE(_data, i);
    i += 2;
    final var epoch = getInt32LE(_data, i);
    i += 4;
    final var latestAggregatorRoundId = getInt32LE(_data, i);
    i += 4;
    final var latestTransmitter = readPubKey(_data, i);
    i += 32;
    final var configCount = getInt32LE(_data, i);
    i += 4;
    final var latestConfigDigest = new byte[32];
    i += Borsh.readArray(latestConfigDigest, _data, i);
    final var latestConfigBlockNumber = getInt64LE(_data, i);
    i += 8;
    final var billing = Billing.read(_data, i);
    return new Config(owner,
                      proposedOwner,
                      tokenMint,
                      tokenVault,
                      requesterAccessController,
                      billingAccessController,
                      minAnswer,
                      maxAnswer,
                      f,
                      round,
                      padding0,
                      epoch,
                      latestAggregatorRoundId,
                      latestTransmitter,
                      configCount,
                      latestConfigDigest,
                      latestConfigBlockNumber,
                      billing);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    owner.write(_data, i);
    i += 32;
    proposedOwner.write(_data, i);
    i += 32;
    tokenMint.write(_data, i);
    i += 32;
    tokenVault.write(_data, i);
    i += 32;
    requesterAccessController.write(_data, i);
    i += 32;
    billingAccessController.write(_data, i);
    i += 32;
    putInt128LE(_data, i, minAnswer);
    i += 16;
    putInt128LE(_data, i, maxAnswer);
    i += 16;
    _data[i] = (byte) f;
    ++i;
    _data[i] = (byte) round;
    ++i;
    putInt16LE(_data, i, padding0);
    i += 2;
    putInt32LE(_data, i, epoch);
    i += 4;
    putInt32LE(_data, i, latestAggregatorRoundId);
    i += 4;
    latestTransmitter.write(_data, i);
    i += 32;
    putInt32LE(_data, i, configCount);
    i += 4;
    i += Borsh.writeArray(latestConfigDigest, _data, i);
    putInt64LE(_data, i, latestConfigBlockNumber);
    i += 8;
    i += Borsh.write(billing, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
