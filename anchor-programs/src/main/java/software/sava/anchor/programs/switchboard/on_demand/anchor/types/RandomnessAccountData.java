package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record RandomnessAccountData(PublicKey _address,
                                    Discriminator discriminator,
                                    PublicKey authority,
                                    PublicKey queue,
                                    byte[] seedSlothash,
                                    long seedSlot,
                                    PublicKey oracle,
                                    long revealSlot,
                                    byte[] value,
                                    long lutSlot,
                                    byte[] ebuf3,
                                    byte[] ebuf2,
                                    byte[] ebuf1,
                                    byte[] activeSecp256k1Signer,
                                    long activeSecp256k1Expiration) implements Borsh {

  public static final int BYTES = 480;
  public static final int SEED_SLOTHASH_LEN = 32;
  public static final int VALUE_LEN = 32;
  public static final int EBUF_3_LEN = 24;
  public static final int EBUF_2_LEN = 64;
  public static final int EBUF_1_LEN = 128;
  public static final int ACTIVE_SECP_222K_1_SIGNER_LEN = 64;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(10, 66, 229, 135, 220, 239, 217, 114);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int AUTHORITY_OFFSET = 8;
  public static final int QUEUE_OFFSET = 40;
  public static final int SEED_SLOTHASH_OFFSET = 72;
  public static final int SEED_SLOT_OFFSET = 104;
  public static final int ORACLE_OFFSET = 112;
  public static final int REVEAL_SLOT_OFFSET = 144;
  public static final int VALUE_OFFSET = 152;
  public static final int LUT_SLOT_OFFSET = 184;
  public static final int EBUF_3_OFFSET = 192;
  public static final int EBUF_2_OFFSET = 216;
  public static final int EBUF_1_OFFSET = 280;
  public static final int ACTIVE_SECP_222K_1_SIGNER_OFFSET = 408;
  public static final int ACTIVE_SECP_222K_1_EXPIRATION_OFFSET = 472;

  public static Filter createAuthorityFilter(final PublicKey authority) {
    return Filter.createMemCompFilter(AUTHORITY_OFFSET, authority);
  }

  public static Filter createQueueFilter(final PublicKey queue) {
    return Filter.createMemCompFilter(QUEUE_OFFSET, queue);
  }

  public static Filter createSeedSlotFilter(final long seedSlot) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, seedSlot);
    return Filter.createMemCompFilter(SEED_SLOT_OFFSET, _data);
  }

  public static Filter createOracleFilter(final PublicKey oracle) {
    return Filter.createMemCompFilter(ORACLE_OFFSET, oracle);
  }

  public static Filter createRevealSlotFilter(final long revealSlot) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, revealSlot);
    return Filter.createMemCompFilter(REVEAL_SLOT_OFFSET, _data);
  }

  public static Filter createLutSlotFilter(final long lutSlot) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lutSlot);
    return Filter.createMemCompFilter(LUT_SLOT_OFFSET, _data);
  }

  public static Filter createActiveSecp256k1ExpirationFilter(final long activeSecp256k1Expiration) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, activeSecp256k1Expiration);
    return Filter.createMemCompFilter(ACTIVE_SECP_222K_1_EXPIRATION_OFFSET, _data);
  }

  public static RandomnessAccountData read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static RandomnessAccountData read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static RandomnessAccountData read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], RandomnessAccountData> FACTORY = RandomnessAccountData::read;

  public static RandomnessAccountData read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var authority = readPubKey(_data, i);
    i += 32;
    final var queue = readPubKey(_data, i);
    i += 32;
    final var seedSlothash = new byte[32];
    i += Borsh.readArray(seedSlothash, _data, i);
    final var seedSlot = getInt64LE(_data, i);
    i += 8;
    final var oracle = readPubKey(_data, i);
    i += 32;
    final var revealSlot = getInt64LE(_data, i);
    i += 8;
    final var value = new byte[32];
    i += Borsh.readArray(value, _data, i);
    final var lutSlot = getInt64LE(_data, i);
    i += 8;
    final var ebuf3 = new byte[24];
    i += Borsh.readArray(ebuf3, _data, i);
    final var ebuf2 = new byte[64];
    i += Borsh.readArray(ebuf2, _data, i);
    final var ebuf1 = new byte[128];
    i += Borsh.readArray(ebuf1, _data, i);
    final var activeSecp256k1Signer = new byte[64];
    i += Borsh.readArray(activeSecp256k1Signer, _data, i);
    final var activeSecp256k1Expiration = getInt64LE(_data, i);
    return new RandomnessAccountData(_address,
                                     discriminator,
                                     authority,
                                     queue,
                                     seedSlothash,
                                     seedSlot,
                                     oracle,
                                     revealSlot,
                                     value,
                                     lutSlot,
                                     ebuf3,
                                     ebuf2,
                                     ebuf1,
                                     activeSecp256k1Signer,
                                     activeSecp256k1Expiration);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    authority.write(_data, i);
    i += 32;
    queue.write(_data, i);
    i += 32;
    i += Borsh.writeArray(seedSlothash, _data, i);
    putInt64LE(_data, i, seedSlot);
    i += 8;
    oracle.write(_data, i);
    i += 32;
    putInt64LE(_data, i, revealSlot);
    i += 8;
    i += Borsh.writeArray(value, _data, i);
    putInt64LE(_data, i, lutSlot);
    i += 8;
    i += Borsh.writeArray(ebuf3, _data, i);
    i += Borsh.writeArray(ebuf2, _data, i);
    i += Borsh.writeArray(ebuf1, _data, i);
    i += Borsh.writeArray(activeSecp256k1Signer, _data, i);
    putInt64LE(_data, i, activeSecp256k1Expiration);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
