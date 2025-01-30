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

public record OracleAccountData(PublicKey _address,
                                Discriminator discriminator,
                                // Represents the state of the quote verifiers enclave.
                                Quote enclave,
                                // The authority of the EnclaveAccount which is permitted to make account changes.
                                PublicKey authority,
                                // Queue used for attestation to verify a MRENCLAVE measurement.
                                PublicKey queue,
                                // The unix timestamp when the quote was created.
                                long createdAt,
                                // The last time the quote heartbeated on-chain.
                                long lastHeartbeat,
                                byte[] secpAuthority,
                                // URI location of the verifier's gateway.
                                byte[] gatewayUri,
                                long permissions,
                                // Whether the quote is located on the AttestationQueues buffer.
                                int isOnQueue,
                                byte[] padding1,
                                long lutSlot,
                                long lastRewardEpoch,
                                byte[] ebuf4,
                                byte[] ebuf3,
                                byte[] ebuf2,
                                byte[] ebuf1) implements Borsh {

  public static final int BYTES = 4816;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(128, 30, 16, 241, 170, 73, 55, 54);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int ENCLAVE_OFFSET = 8;
  public static final int AUTHORITY_OFFSET = 3440;
  public static final int QUEUE_OFFSET = 3472;
  public static final int CREATED_AT_OFFSET = 3504;
  public static final int LAST_HEARTBEAT_OFFSET = 3512;
  public static final int SECP_AUTHORITY_OFFSET = 3520;
  public static final int GATEWAY_URI_OFFSET = 3584;
  public static final int PERMISSIONS_OFFSET = 3648;
  public static final int IS_ON_QUEUE_OFFSET = 3656;
  public static final int PADDING1_OFFSET = 3657;
  public static final int LUT_SLOT_OFFSET = 3664;
  public static final int LAST_REWARD_EPOCH_OFFSET = 3672;
  public static final int EBUF4_OFFSET = 3680;
  public static final int EBUF3_OFFSET = 3696;
  public static final int EBUF2_OFFSET = 3728;
  public static final int EBUF1_OFFSET = 3792;

  public static Filter createAuthorityFilter(final PublicKey authority) {
    return Filter.createMemCompFilter(AUTHORITY_OFFSET, authority);
  }

  public static Filter createQueueFilter(final PublicKey queue) {
    return Filter.createMemCompFilter(QUEUE_OFFSET, queue);
  }

  public static Filter createCreatedAtFilter(final long createdAt) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, createdAt);
    return Filter.createMemCompFilter(CREATED_AT_OFFSET, _data);
  }

  public static Filter createLastHeartbeatFilter(final long lastHeartbeat) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastHeartbeat);
    return Filter.createMemCompFilter(LAST_HEARTBEAT_OFFSET, _data);
  }

  public static Filter createPermissionsFilter(final long permissions) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, permissions);
    return Filter.createMemCompFilter(PERMISSIONS_OFFSET, _data);
  }

  public static Filter createIsOnQueueFilter(final int isOnQueue) {
    return Filter.createMemCompFilter(IS_ON_QUEUE_OFFSET, new byte[]{(byte) isOnQueue});
  }

  public static Filter createLutSlotFilter(final long lutSlot) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lutSlot);
    return Filter.createMemCompFilter(LUT_SLOT_OFFSET, _data);
  }

  public static Filter createLastRewardEpochFilter(final long lastRewardEpoch) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastRewardEpoch);
    return Filter.createMemCompFilter(LAST_REWARD_EPOCH_OFFSET, _data);
  }

  public static OracleAccountData read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static OracleAccountData read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static OracleAccountData read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], OracleAccountData> FACTORY = OracleAccountData::read;

  public static OracleAccountData read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var enclave = Quote.read(_data, i);
    i += Borsh.len(enclave);
    final var authority = readPubKey(_data, i);
    i += 32;
    final var queue = readPubKey(_data, i);
    i += 32;
    final var createdAt = getInt64LE(_data, i);
    i += 8;
    final var lastHeartbeat = getInt64LE(_data, i);
    i += 8;
    final var secpAuthority = new byte[64];
    i += Borsh.readArray(secpAuthority, _data, i);
    final var gatewayUri = new byte[64];
    i += Borsh.readArray(gatewayUri, _data, i);
    final var permissions = getInt64LE(_data, i);
    i += 8;
    final var isOnQueue = _data[i] & 0xFF;
    ++i;
    final var padding1 = new byte[7];
    i += Borsh.readArray(padding1, _data, i);
    final var lutSlot = getInt64LE(_data, i);
    i += 8;
    final var lastRewardEpoch = getInt64LE(_data, i);
    i += 8;
    final var ebuf4 = new byte[16];
    i += Borsh.readArray(ebuf4, _data, i);
    final var ebuf3 = new byte[32];
    i += Borsh.readArray(ebuf3, _data, i);
    final var ebuf2 = new byte[64];
    i += Borsh.readArray(ebuf2, _data, i);
    final var ebuf1 = new byte[1024];
    Borsh.readArray(ebuf1, _data, i);
    return new OracleAccountData(_address,
                                 discriminator,
                                 enclave,
                                 authority,
                                 queue,
                                 createdAt,
                                 lastHeartbeat,
                                 secpAuthority,
                                 gatewayUri,
                                 permissions,
                                 isOnQueue,
                                 padding1,
                                 lutSlot,
                                 lastRewardEpoch,
                                 ebuf4,
                                 ebuf3,
                                 ebuf2,
                                 ebuf1);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.write(enclave, _data, i);
    authority.write(_data, i);
    i += 32;
    queue.write(_data, i);
    i += 32;
    putInt64LE(_data, i, createdAt);
    i += 8;
    putInt64LE(_data, i, lastHeartbeat);
    i += 8;
    i += Borsh.writeArray(secpAuthority, _data, i);
    i += Borsh.writeArray(gatewayUri, _data, i);
    putInt64LE(_data, i, permissions);
    i += 8;
    _data[i] = (byte) isOnQueue;
    ++i;
    i += Borsh.writeArray(padding1, _data, i);
    putInt64LE(_data, i, lutSlot);
    i += 8;
    putInt64LE(_data, i, lastRewardEpoch);
    i += 8;
    i += Borsh.writeArray(ebuf4, _data, i);
    i += Borsh.writeArray(ebuf3, _data, i);
    i += Borsh.writeArray(ebuf2, _data, i);
    i += Borsh.writeArray(ebuf1, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
