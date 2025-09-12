package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import java.util.OptionalLong;
import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record TokenRecord(PublicKey _address,
                          Key key,
                          int bump,
                          TokenState state,
                          OptionalLong ruleSetRevision,
                          PublicKey delegate,
                          TokenDelegateRole delegateRole,
                          PublicKey lockedTransfer) implements Borsh {

  public static final int KEY_OFFSET = 0;
  public static final int BUMP_OFFSET = 1;
  public static final int STATE_OFFSET = 2;
  public static final int RULE_SET_REVISION_OFFSET = 3;

  public static Filter createKeyFilter(final Key key) {
    return Filter.createMemCompFilter(KEY_OFFSET, key.write());
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createStateFilter(final TokenState state) {
    return Filter.createMemCompFilter(STATE_OFFSET, state.write());
  }

  public static Filter createRuleSetRevisionFilter(final long ruleSetRevision) {
    final byte[] _data = new byte[9];
    _data[0] = 1;
    putInt64LE(_data, 1, ruleSetRevision);
    return Filter.createMemCompFilter(RULE_SET_REVISION_OFFSET, _data);
  }

  public static TokenRecord read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static TokenRecord read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static TokenRecord read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], TokenRecord> FACTORY = TokenRecord::read;

  public static TokenRecord read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var key = Key.read(_data, i);
    i += Borsh.len(key);
    final var bump = _data[i] & 0xFF;
    ++i;
    final var state = TokenState.read(_data, i);
    i += Borsh.len(state);
    final var ruleSetRevision = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (ruleSetRevision.isPresent()) {
      i += 8;
    }
    final var delegate = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (delegate != null) {
      i += 32;
    }
    final var delegateRole = _data[i++] == 0 ? null : TokenDelegateRole.read(_data, i);
    if (delegateRole != null) {
      i += Borsh.len(delegateRole);
    }
    final var lockedTransfer = _data[i++] == 0 ? null : readPubKey(_data, i);
    return new TokenRecord(_address,
                           key,
                           bump,
                           state,
                           ruleSetRevision,
                           delegate,
                           delegateRole,
                           lockedTransfer);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(key, _data, i);
    _data[i] = (byte) bump;
    ++i;
    i += Borsh.write(state, _data, i);
    i += Borsh.writeOptional(ruleSetRevision, _data, i);
    i += Borsh.writeOptional(delegate, _data, i);
    i += Borsh.writeOptional(delegateRole, _data, i);
    i += Borsh.writeOptional(lockedTransfer, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(key)
         + 1
         + Borsh.len(state)
         + (ruleSetRevision == null || ruleSetRevision.isEmpty() ? 1 : (1 + 8))
         + (delegate == null ? 1 : (1 + 32))
         + (delegateRole == null ? 1 : (1 + Borsh.len(delegateRole)))
         + (lockedTransfer == null ? 1 : (1 + 32));
  }
}
